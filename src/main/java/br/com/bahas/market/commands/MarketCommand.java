package br.com.bahas.market.commands;

import br.com.bahas.market.entities.MarketItem;
import br.com.bahas.market.inventory.MarketInventoryHolder;
import br.com.bahas.market.service.MarketInventoryService;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AllArgsConstructor
public class MarketCommand implements CommandExecutor {

    private final MarketInventoryService marketInventoryService;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player player)) return false;

        if (args.length == 0) {
            MarketInventoryHolder inventoryHolder = new MarketInventoryHolder();
            Inventory inventory = inventoryHolder.getInventory();

            List<MarketItem> items = marketInventoryService.getAllItems();

            items.forEach(marketItem -> inventory.addItem(marketItem.getItemStack().clone()));

            player.openInventory(inventory);

            return true;
        }

        switch (args[0]) {
            case "vender" -> {
                // logica de venda
            }
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
}
