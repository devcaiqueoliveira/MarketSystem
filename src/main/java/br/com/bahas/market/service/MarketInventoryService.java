package br.com.bahas.market.service;

import br.com.bahas.market.cache.MarketInventoryCache;
import br.com.bahas.market.entities.MarketItem;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MarketInventoryService {

    private final MarketInventoryCache marketInventoryCache;

    public void addItemOnMarket(MarketItem item) {
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("O preço do item não pode ser negativo.");
        }
        marketInventoryCache.add(item);
    }

    public void removeItemFromMarket(MarketItem item) {
        marketInventoryCache.remove(item);
    }

    public List<MarketItem> getAllItems() {
        return marketInventoryCache.listAll();
    }
}