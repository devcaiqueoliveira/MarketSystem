package br.com.bahas.market.repository;

import br.com.bahas.market.entities.MarketItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketRepository {

    void save(MarketItem marketItem);
    void delete(MarketItem marketItem);
    List<MarketItem> findAll();
    List<MarketItem> findAllById(UUID uuid);
    MarketItem findByPlayerName(UUID uuid, String value);
}