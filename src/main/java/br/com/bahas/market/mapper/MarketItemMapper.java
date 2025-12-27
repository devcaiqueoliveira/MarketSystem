package br.com.bahas.market.mapper;

import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.utils.ItemBuilder;
import br.com.bahas.market.utils.MarketKeys;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class MarketItemMapper {

    public static MarketItem toMarketItem(ItemStack item, UUID sellerId, double price) {

        return MarketItem.builder()
                .itemStack(item.clone())
                .sellerUUID(sellerId)
                .price(price)
                .timeStamp(System.currentTimeMillis())
                .transactionUUID(UUID.randomUUID())
                .build();
    }

    public static ItemStack toVisualItem(JavaPlugin plugin, MarketItem marketItem) {

        String sellerName = Bukkit.getOfflinePlayer(marketItem.getSellerUUID()).getName();
        if (sellerName == null) sellerName = "Desconhecido";

        return ItemBuilder.from(plugin, marketItem.getItemStack().clone())
                .lore(List.of(
                        "",
                        "<gray>Preço: <green>$" + marketItem.getPrice(),
                        "<gray>Vendedor: <yellow>" + sellerName,
                        "",
                        "<yellow>Clique para comprar!"
                ))
                .pdc(MarketKeys.PRICE, PersistentDataType.DOUBLE, marketItem.getPrice())
                .pdc(MarketKeys.TRANSACTION_ID, PersistentDataType.STRING, marketItem.getTransactionUUID().toString())
                .pdc(MarketKeys.SELLER_ID, PersistentDataType.STRING, marketItem.getSellerUUID().toString())
                .build();
    }
}
