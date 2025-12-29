package br.com.bahas.market.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessageConfig {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public MessageConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        createConfig();
    }

    private void createConfig() {
        configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);


    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void send(CommandSender sender, String path, TagResolver... placeholders) {
        String rawMessage = config.getString(path);

        if (rawMessage == null) {
            sender.sendMessage("Erro: Mensagem não encontrada no arquivo (" + path + ")");
            return;
        }

        String prefix = config.getString("prefix", "");

        Component message = MiniMessage.miniMessage().deserialize(prefix + rawMessage, placeholders);

        sender.sendMessage(message);
    }
}
