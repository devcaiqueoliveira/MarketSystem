package br.com.bahas.market.inventory.impl;

import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.repository.MarketRepository;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AllArgsConstructor
public class PersonalMarketInventory implements MarketInventoryHolder {

    private final MarketRepository repository;

    public void openInventory(Player player) {
        Inventory mercadoPessoal = Bukkit.createInventory(null, 54, "Mercado Pessoal");
        List<MarketItem> allById = repository.findAllById(player.getUniqueId());
        allById.forEach(item -> mercadoPessoal.addItem(item.getItemStack()));
        player.openInventory(mercadoPessoal);
    }

    @Override
    public void handle(InventoryClickEvent event) {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}