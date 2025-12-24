package br.com.bahas.market;

import br.com.bahas.market.cache.MarketInventoryCache;
import br.com.bahas.market.commands.MarketCommand;
import br.com.bahas.market.listener.MarketInventoryListener;
import br.com.bahas.market.service.MarketInventoryService;
import org.bukkit.plugin.java.JavaPlugin;

public final class Market extends JavaPlugin {

    @Override
    public void onEnable() {
        MarketInventoryCache marketInventoryCache = new MarketInventoryCache();

        MarketInventoryService marketInventoryService = new MarketInventoryService(marketInventoryCache);

        getCommand("mercado").setExecutor(new MarketCommand(marketInventoryService, this));

        getServer().getPluginManager().registerEvents(new MarketInventoryListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
