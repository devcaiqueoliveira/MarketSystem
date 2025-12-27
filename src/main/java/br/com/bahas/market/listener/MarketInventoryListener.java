package br.com.bahas.market.listener;

import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.service.FakeEconomyService;
import br.com.bahas.market.service.MarketInventoryService;
import br.com.bahas.market.utils.MarketKeys;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class MarketInventoryListener implements Listener {

    private final JavaPlugin plugin;
    private final MarketInventoryService marketInventoryService;
    private final FakeEconomyService economyService;
    private final MessageConfig messageConfig;

    @EventHandler
    public void onMarketClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MarketInventoryHolder)) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        PersistentDataContainer pdc = clicked.getItemMeta().getPersistentDataContainer();

        NamespacedKey transactionKey = new NamespacedKey(plugin, MarketKeys.TRANSACTION_ID);

        if (!pdc.has(transactionKey, PersistentDataType.STRING)) return;

        Player player = (Player) event.getWhoClicked();
        String transactionIdStr = pdc.get(transactionKey, PersistentDataType.STRING);

        UUID transactionId = UUID.fromString(transactionIdStr);

        Optional<MarketItem> itemOpt = marketInventoryService.findByTransactionId(transactionId);

        if (itemOpt.isEmpty()) {
            messageConfig.send(player, "messages.errors.item-already-sold");
            event.getInventory().remove(clicked);
            return;
        }

        MarketItem marketItem = itemOpt.get();
        boolean isOwner = marketItem.getSellerUUID().equals(player.getUniqueId());

        if (isOwner) {
            if (event.isRightClick()) {
                handleRemoveItem(player, marketItem, clicked, event);
                return;
            }
            player.sendMessage("Clique com o botão direito para remover seu item.");
            return;
        }
        handleBuyItem(player, marketItem, clicked, event);

    }

    private void handleRemoveItem(Player player, MarketItem marketItem, ItemStack visualItem, InventoryClickEvent event) {
        if (player.getInventory().firstEmpty() == -1) {
            messageConfig.send(player, "messages.errors.inventory-full");
            return;
        }

        player.getInventory().addItem(marketItem.getItemStack());

        marketInventoryService.removeItemFromMarket(marketItem);

        event.getInventory().remove(visualItem);

        messageConfig.send(player, "messages.success.item-removed");
    }

    private void handleBuyItem(Player buyer, MarketItem marketItem, ItemStack visualitem, InventoryClickEvent event) {
        if (!economyService.has(buyer.getUniqueId(), marketItem.getPrice())) {
            messageConfig.send(buyer, "messages.errors.insufficient-funds",
                    Placeholder.parsed("balance", String.valueOf(economyService.getBalance(buyer.getUniqueId()))));
            return;
        }

        economyService.withdraw(buyer.getUniqueId(), marketItem.getPrice());
        economyService.deposit(marketItem.getSellerUUID(), marketItem.getPrice());

        buyer.getInventory().addItem(marketItem.getItemStack());

        marketInventoryService.removeItemFromMarket(marketItem);
        event.getInventory().remove(visualitem);

        messageConfig.send(buyer, "messages.success.item-bought",
                Placeholder.parsed("price", String.valueOf(marketItem.getPrice())));
    }

}
