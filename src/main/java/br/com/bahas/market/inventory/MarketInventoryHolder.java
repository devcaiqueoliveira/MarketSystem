package br.com.bahas.market.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MarketInventoryHolder implements InventoryHolder {

    private static Inventory inventory;

    public MarketInventoryHolder() {
        inventory = Bukkit.createInventory(this, 54, "Mercado");
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}