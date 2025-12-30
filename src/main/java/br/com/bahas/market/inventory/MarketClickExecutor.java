package br.com.bahas.market.inventory;

import br.com.bahas.market.inventory.impl.PersonalMarketInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MarketClickExecutor {

    private MarketClickExecutor() {
    }

    public static void execute(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof PersonalMarketInventory personalMarketInventory) {
            personalMarketInventory.handle(event);
            return;
        }

    }
}
