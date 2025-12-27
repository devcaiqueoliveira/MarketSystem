package br.com.bahas.market.cache;

import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.repository.MarketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MarketInventoryCache implements MarketRepository {

    private List<MarketItem> items = new ArrayList<>();

    @Override
    public void add(MarketItem marketItem) {
        items.add(marketItem);
    }
    @Override
    public void remove(MarketItem marketItem) {
        items.remove(marketItem);
    }

    @Override
    public List<MarketItem> listAll() {
        return new ArrayList<>(items);
    }

    @Override
    public Optional<MarketItem> findByTransactionId(UUID uuid) {
        return listAll().stream()
                .filter(item -> item.getTransactionUUID().equals(uuid))
                .findFirst();
    }
}