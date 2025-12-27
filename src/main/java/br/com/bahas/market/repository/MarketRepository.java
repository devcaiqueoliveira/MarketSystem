package br.com.bahas.market.repository;

import br.com.bahas.market.entities.MarketItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketRepository {

    void add(MarketItem marketItem);

    void remove(MarketItem marketItem);

    List<MarketItem> listAll();

    Optional<MarketItem> findByTransactionId(UUID uuid);
}