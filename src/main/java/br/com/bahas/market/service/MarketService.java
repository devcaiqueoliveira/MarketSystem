package br.com.bahas.market.service;

import br.com.bahas.market.cache.MarketCache;
import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.entities.MarketItem;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class MarketService {

    private final MarketCache cache;
    private final FakeEconomyService economyService;
    private final MessageConfig messageConfig;

    public void addItemOnMarket(MarketItem item) {
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("O preço do item não pode ser negativo.");
        }
        cache.save(item);
    }

    public boolean purchaseItem(Player buyer, MarketItem item) {
        if (!economyService.has(buyer.getUniqueId(), item.getPrice())) {
            messageConfig.send(buyer, "messages.errors.insufficient-funds");
            return false;
        }

        if (isInventoryFull(buyer)) {
            return false;
        }

        economyService.withdraw(buyer.getUniqueId(), item.getPrice());
        economyService.deposit(item.getSellerUUID(), item.getPrice());
        cache.delete(item);
        buyer.getInventory().addItem(item.getItemStack());

        messageConfig.send(buyer, "messages.succes.item-bought",
                Placeholder.parsed("price", String.valueOf(item.getPrice())));
        return true;
    }

    public boolean processWithdraw(Player owner, MarketItem item) {
        if (isInventoryFull(owner)) {
            return false;
        }
        cache.delete(item);
        owner.getInventory().addItem(item.getItemStack());

        messageConfig.send(owner, "messages.success.item-removed");
        return true;
    }

    public List<MarketItem> getItemsBySeller(UUID sellerId) {
        return cache.findAll().stream()
                .filter(item -> item.getSellerUUID().equals(sellerId))
                .toList();
    }

    public Optional<MarketItem> findByTransactionId(UUID uuid) {
        return cache.findByTransactionId(uuid);
    }

    private boolean isInventoryFull(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            messageConfig.send(player, "messages.warn.inventory-full");
            return true;
        }
        return false;
    }
}
