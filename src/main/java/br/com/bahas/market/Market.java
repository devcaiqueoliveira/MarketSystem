package br.com.bahas.market;

import br.com.bahas.market.cache.MarketCache;
import br.com.bahas.market.commands.MarketCommand;
import br.com.bahas.market.config.MessageConfig;
import br.com.bahas.market.inventory.impl.PersonalMarketInventory;
import br.com.bahas.market.listener.MarketInventoryListener;
import br.com.bahas.market.repository.MarketRepository;
import br.com.bahas.market.service.FakeEconomyService;
import br.com.bahas.market.service.MarketService;
import org.bukkit.plugin.java.JavaPlugin;

public final class Market extends JavaPlugin {

    private MessageConfig messageConfig;

    @Override
    public void onEnable() {
        MarketRepository repository = new MarketCache();

        FakeEconomyService fakeEconomyService = new FakeEconomyService();

        PersonalMarketInventory personalMarketInventory = new PersonalMarketInventory(repository);

        this.messageConfig = new MessageConfig(this);

        MarketService marketService = new MarketService(repository, fakeEconomyService, messageConfig);

        getCommand("mercado").setExecutor(new MarketCommand(marketService, personalMarketInventory, messageConfig, this));

        getServer().getPluginManager().registerEvents(new MarketInventoryListener(this, marketService, fakeEconomyService, messageConfig), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
