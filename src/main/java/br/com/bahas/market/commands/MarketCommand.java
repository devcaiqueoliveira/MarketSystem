package br.com.bahas.market.commands;

import br.com.bahas.market.Market;
import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.service.MarketInventoryService;
import br.com.bahas.market.utils.ItemBuilder;
import br.com.bahas.market.utils.MarketKeys;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class MarketCommand implements CommandExecutor {

    private final MarketInventoryService marketInventoryService;

    private final MarketInventoryHolder inventoryHolder = new MarketInventoryHolder();

    private final JavaPlugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player player)) return false;

        if (args.length == 0) {
            Inventory inventory = inventoryHolder.getInventory();
            player.openInventory(inventory);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "vender" -> handleSell(player, args);
            case "retirar" -> {
                // logica de retirar item
            }
            default -> {
                player.sendMessage("Opção inválida.");
                break;
            }
        }

        return true;
    }

    private void handleSell(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Uso: /mercado vender <preco>");
            return;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.getType() == Material.AIR) {
            player.sendMessage("Você precisa ter um item na mão");
            return;
        }

        try {
            double price = Double.parseDouble(args[1]);
            UUID transactionId = UUID.randomUUID();

            MarketItem marketItem = MarketItem.builder()
                    .itemStack(handItem.clone())
                    .sellerUUID(player.getUniqueId())
                    .price(price)
                    .timeStamp(System.currentTimeMillis())
                    .transactionUUID(transactionId)
                    .build();

            marketInventoryService.addItemOnMarket(marketItem);

            ItemStack visualItem = ItemBuilder.from(plugin, handItem.clone())
                    .lore(List.of(
                            "",
                            "<gray>Preço: <green>$" + price,
                            "<gray>Vendedor: <yellow>" + player.getName(),
                            "",
                            "<yellow>Clique para comprar!"
                    ))
                    .pdc(MarketKeys.PRICE, PersistentDataType.DOUBLE, price)
                    .pdc(MarketKeys.TRANSACTION_ID, PersistentDataType.STRING, transactionId.toString())
                    .pdc(MarketKeys.SELLER_ID, PersistentDataType.STRING, player.getUniqueId().toString())
                    .build();

            inventoryHolder.getInventory().addItem(visualItem);

            player.getInventory().setItemInMainHand(null);
            player.sendMessage("§aItem colocado à venda por $" + price);
        } catch (NumberFormatException e) {
            player.sendMessage("§cPreço inválido.");
        }
    }
}
