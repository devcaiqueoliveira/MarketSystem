package br.com.bahas.market.listener;

import br.com.bahas.market.Market;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.utils.MarketKeys;
import lombok.AllArgsConstructor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

@AllArgsConstructor
public class MarketInventoryListener implements Listener {

    private final JavaPlugin plugin;

    @EventHandler
    public void onMarketClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MarketInventoryHolder) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            PersistentDataContainer pdc = clicked.getItemMeta().getPersistentDataContainer();

            NamespacedKey transactionKey = new NamespacedKey(plugin, MarketKeys.TRANSACTION_ID);
            NamespacedKey priceKey = new NamespacedKey(plugin, MarketKeys.PRICE);

            if (!pdc.has(transactionKey, PersistentDataType.STRING)) return;

            Player buyer = (Player) event.getWhoClicked();
            String transactionIdStr = pdc.get(transactionKey, PersistentDataType.STRING);
            Double price = pdc.get(priceKey, PersistentDataType.DOUBLE);

            buyer.sendMessage("Lógica futura de compra de item...");

        }
    }

}
