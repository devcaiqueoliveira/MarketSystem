package br.com.bahas.market.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface MarketInventoryHolder extends InventoryHolder{
    void handle(InventoryClickEvent event);
}
