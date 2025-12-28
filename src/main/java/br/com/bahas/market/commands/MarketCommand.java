package br.com.bahas.market.commands;

import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.mapper.MarketItemMapper;
import br.com.bahas.market.service.MarketInventoryService;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public class MarketCommand implements CommandExecutor {

    private final MarketInventoryService service;

    private final MarketInventoryHolder inventoryHolder = new MarketInventoryHolder();

    private final MessageConfig messageConfig;

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
            case "reload" -> {
                messageConfig.reload();
                messageConfig.send(player, "messages.success.reload");
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
            messageConfig.send(player, "messages.errors.invalid-args");
            return;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.getType() == Material.AIR) {
            messageConfig.send(player, "messages.errors.no-item");
            return;
        }

        try {

            double price = Double.parseDouble(args[1]);
            UUID transactionId = UUID.randomUUID();

            MarketItem marketItem = MarketItemMapper.toMarketItem(handItem, player.getUniqueId(), price);

            service.addItemOnMarket(marketItem);

            ItemStack visualItem = MarketItemMapper.toVisualItem(plugin, marketItem);

            inventoryHolder.getInventory().addItem(visualItem);

            player.getInventory().setItemInMainHand(null);
            messageConfig.send(player, "messages.success.item-listed",
                    Placeholder.parsed("price", String.valueOf(price)));

        } catch (NumberFormatException e) {
            messageConfig.send(player, "messages.errors.invalid-price");
        }
    }
}
