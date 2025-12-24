package br.com.bahas.market.utils;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final JavaPlugin plugin;

    private ItemBuilder(@NonNull JavaPlugin plugin, @NonNull Material material) {
        this.plugin = plugin;
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    private ItemBuilder(@NonNull JavaPlugin plugin, @NonNull ItemStack itemStack) {
        this.plugin = plugin;
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public static ItemBuilder of(@NonNull JavaPlugin plugin, @NonNull Material material) {
        return new ItemBuilder(plugin, material);
    }

    public ItemBuilder name(@NonNull String miniMessage) {
        itemMeta.displayName(MiniMessage.miniMessage().deserialize(miniMessage));
        return this;
    }

    public ItemBuilder lore(@NonNull List<String> lines) {
        List<Component> components = new ArrayList<>();
        lines.forEach(line -> components.add(MiniMessage.miniMessage().deserialize(line)));
        itemMeta.lore(components);
        return this;
    }

    public ItemBuilder customModelData(int data) {
        itemMeta.setCustomModelData(data);
        return this;
    }

    public <T, Z> ItemBuilder pdc(@NonNull String key, @NonNull PersistentDataType<T, Z> type, @NonNull Z value) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        itemMeta.getPersistentDataContainer().set(namespacedKey, type, value);
        return this;
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(itemMeta);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemBuilder from(@NonNull JavaPlugin plugin, @NonNull ItemStack itemStack) {
        return new ItemBuilder(plugin, itemStack);
    }
}