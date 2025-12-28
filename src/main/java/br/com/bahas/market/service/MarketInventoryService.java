package br.com.bahas.market.service;

import br.com.bahas.market.cache.MarketInventoryCache;
import br.com.bahas.market.entities.MarketItem;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class MarketInventoryService {

    private final MarketInventoryCache marketInventoryCache;

    public void addItemOnMarket(MarketItem item) {
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("O preço do item não pode ser negativo.");
        }
        marketInventoryCache.save(item);
    }

    public void removeItemFromMarket(MarketItem item) {
        marketInventoryCache.delete(item);
    }

    public List<MarketItem> getAllItems() {
        return marketInventoryCache.listAll();
    }

    public Optional<MarketItem> findByTransactionId(UUID uuid) {
        return marketInventoryCache.findByTransactionId(uuid);
    }
}