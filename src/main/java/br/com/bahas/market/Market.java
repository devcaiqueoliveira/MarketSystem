package br.com.bahas.market;

import br.com.bahas.market.cache.MarketInventoryCache;
import br.com.bahas.market.commands.MarketCommand;
import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.listener.MarketInventoryListener;
import br.com.bahas.market.service.FakeEconomyService;
import br.com.bahas.market.service.MarketInventoryService;
import org.bukkit.plugin.java.JavaPlugin;

public final class Market extends JavaPlugin {

    private MessageConfig messageConfig;

    @Override
    public void onEnable() {
        MarketInventoryCache marketInventoryCache = new MarketInventoryCache();

        MarketInventoryService marketInventoryService = new MarketInventoryService(marketInventoryCache);

        FakeEconomyService fakeEconomyService = new FakeEconomyService();

        this.messageConfig = new MessageConfig(this);

        getCommand("mercado").setExecutor(new MarketCommand(marketInventoryService, messageConfig, this));

        getServer().getPluginManager().registerEvents(new MarketInventoryListener(this, marketInventoryService, fakeEconomyService, messageConfig), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
