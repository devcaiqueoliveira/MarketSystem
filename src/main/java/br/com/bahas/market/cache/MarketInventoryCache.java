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
    public void save(MarketItem marketItem) {
        items.add(marketItem);
    }
    @Override
    public void delete(MarketItem marketItem) {
        items.remove(marketItem);
    }

    @Override
    public List<MarketItem> findAll() {
        return new ArrayList<>(items);
    }

    @Override
    public Optional<MarketItem> findByTransactionId(UUID uuid) {
        return findAll().stream()
                .filter(item -> item.getTransactionUUID().equals(uuid))
                .findFirst();
    }
}