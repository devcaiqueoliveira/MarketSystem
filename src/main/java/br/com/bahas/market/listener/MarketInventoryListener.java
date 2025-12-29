package br.com.bahas.market.listener;

import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.service.FakeEconomyService;
import br.com.bahas.market.service.MarketService;
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
    private final MarketService service;
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

        Optional<MarketItem> itemOpt = service.findByTransactionId(transactionId);

        MarketItem marketItem = itemOpt.get();
        boolean isOwner = marketItem.getSellerUUID().equals(player.getUniqueId());

        if (isOwner) {
            if (event.isRightClick()) {
                boolean success = service.processWithdraw(player, marketItem);
                if (success) {
                    event.getInventory().remove(clicked);
                }
                return;
            }
            messageConfig.send(player, "messages.warn.right-click-to-remove");
            return;
        }
        boolean success = service.purchaseItem(player, marketItem);
        if (success) {
            event.getInventory().remove(clicked);
        }
    }


}
