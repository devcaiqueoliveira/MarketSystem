package br.com.bahas.market.cache;

import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.repository.MarketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}