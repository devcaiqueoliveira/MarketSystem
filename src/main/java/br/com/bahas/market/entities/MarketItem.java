package br.com.bahas.market.entities;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
@Builder
public class MarketItem {
    private ItemStack itemStack;
    private UUID sellerUUID;
    private double price;
    private Long timeStamp;
    private UUID transactionUUID;
}