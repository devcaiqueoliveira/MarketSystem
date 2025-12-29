package br.com.bahas.market;

import br.com.bahas.market.cache.MarketCache;
import br.com.bahas.market.commands.MarketCommand;
import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.listener.MarketInventoryListener;
import br.com.bahas.market.service.FakeEconomyService;
import br.com.bahas.market.service.MarketService;
import org.bukkit.plugin.java.JavaPlugin;

public final class Market extends JavaPlugin {

    private MessageConfig messageConfig;

    @Override
    public void onEnable() {
        MarketCache marketCache = new MarketCache();

        FakeEconomyService fakeEconomyService = new FakeEconomyService();

        this.messageConfig = new MessageConfig(this);

        MarketService marketService = new MarketService(marketCache, fakeEconomyService, messageConfig);

        getCommand("mercado").setExecutor(new MarketCommand(marketService, messageConfig, this));

        getServer().getPluginManager().registerEvents(new MarketInventoryListener(this, marketService, fakeEconomyService, messageConfig), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
