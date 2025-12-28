package br.com.bahas.market.service;

import br.com.bahas.market.cache.MarketInventoryCache;
import br.com.bahas.market.entities.MarketItem;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class MarketInventoryService {

    private final MarketInventoryCache cache;

    public void addItemOnMarket(MarketItem item) {
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("O preço do item não pode ser negativo.");
        }
        cache.save(item);
    }

    public void removeItemFromMarket(MarketItem item) {
        cache.delete(item);
    }

    public Optional<MarketItem> findByTransactionId(UUID uuid) {
        return cache.findByTransactionId(uuid);
    }
}