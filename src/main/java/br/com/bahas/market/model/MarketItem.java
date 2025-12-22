package br.com.bahas.market.model;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MarketItem {
    private ItemStack itemStack;
    private UUID sellerUUID;
    private double price;
    private Long timeStamp;
    private UUID transactionUUID;
}
