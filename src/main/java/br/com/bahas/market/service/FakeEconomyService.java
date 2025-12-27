package br.com.bahas.market.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakeEconomyService {

    private final Map<UUID, Double> accounts = new HashMap<>();

    public double getBalance(UUID uuid) {
        return accounts.getOrDefault(uuid, 1000.0);
    }

    public boolean has(UUID uuid, double amount) {
        return getBalance(uuid) >= amount;
    }

    public void withdraw(UUID uuid, double amount) {
        accounts.put(uuid, getBalance(uuid) - amount);
    }

    public void deposit(UUID uuid, double amout) {
        accounts.put(uuid, getBalance(uuid) + amout);
    }
}
