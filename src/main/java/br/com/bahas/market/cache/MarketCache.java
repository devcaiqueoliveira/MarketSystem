package br.com.bahas.market.cache;

import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.repository.MarketRepository;

import java.util.*;

public class MarketCache implements MarketRepository {

    private Map<UUID, List<MarketItem>> items = new HashMap();

    @Override
    public void save(MarketItem marketItem) {
        items.computeIfAbsent(marketItem.getSellerUUID(), id -> new ArrayList<>()).add(marketItem);
    }

    @Override
    public void delete(MarketItem marketItem) {
        items.remove(marketItem.getSellerUUID(), marketItem);
    }

    @Override
    public List<MarketItem> findAll() {
        return null;
    }

    @Override
    public List<MarketItem> findAllById(UUID uuid) {
        return items.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public MarketItem findByPlayerName(UUID id, String value) {
        List<MarketItem> marketItems = items.get(id);
        for (MarketItem item : marketItems) {
            if (item.toString().equals(value)) {
                return item;
            }
        }
        return null;
    }
}