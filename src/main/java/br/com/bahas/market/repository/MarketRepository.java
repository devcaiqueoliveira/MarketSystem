package br.com.bahas.market.repository;

import br.com.bahas.market.entities.MarketItem;

import java.util.List;

public interface MarketRepository {

    void add(MarketItem marketItem);
    void remove(MarketItem marketItem);
    List<MarketItem> listAll();

}