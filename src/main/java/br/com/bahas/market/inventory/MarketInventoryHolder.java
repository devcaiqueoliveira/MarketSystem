package br.com.bahas.market.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public class MarketInventoryHolder implements InventoryHolder {

    private Inventory inventory;

    public MarketInventoryHolder() {
        this.inventory = Bukkit.createInventory(this, 54, "Mercado");
    }
}