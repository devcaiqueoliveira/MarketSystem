package br.com.bahas.market.inventory.impl;

import br.com.bahas.market.inventory.MarketInventoryHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketInventory implements MarketInventoryHolder {

    public void openInventory(Player player) {
        Inventory mercadoPessoal = getInventory();
        player.openInventory(mercadoPessoal);
    }

    @Override
    public void handle(InventoryClickEvent event) {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return Bukkit.createInventory(null, 54, "Mercado Pessoal");
    }
}