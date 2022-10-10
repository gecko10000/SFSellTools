package gecko10000.sfselltools;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemManager {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ItemManager() {
        initItems();
    }

    private final NamespacedKey wandKey = NamespacedKey.fromString("sfselltools:wands"), cellKey = NamespacedKey.fromString("sfselltools:cells");

    private void initItems() {
        ItemGroup wandGroup = new ItemGroup(wandKey, new CustomItemStack(Material.STICK, "&aSell Wands"));
        createSellWand(wandGroup, 1, new ItemStack[0], 10000, 10);
        createSellWand(wandGroup, 2, new ItemStack[0], 50000, 5);
        createSellWand(wandGroup, 3, new ItemStack[0], 100000, 1);
        createSellWand(wandGroup, 4, new ItemStack[0], 1000000, 0.01);
    }

    private void createSellWand(ItemGroup group, int tier, ItemStack[] recipe, double maxCharge, double perItem) {
        new SellWand(group, makeWand(tier, maxCharge), recipe, maxCharge, perItem);
    }

    private SlimefunItemStack makeWand(int tier, double maxCharge) {
        return new SlimefunItemStack("SELL_WAND_" + tier, Material.STICK, m -> {
            // i want to hurt somebody
            m.displayName(miniMessage.deserialize("<#" + getColor(tier) + ">Sell Wand " + tier));
            m.setLore(List.of(color(LoreBuilder.powerCharged(0, (int) maxCharge))));
            glow(m);
        });
    }

    private void glow(ItemMeta meta) {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private String getColor(int tier) {
        return tier == 1 ? "026600" : tier == 2 ? "039900" : tier == 3 ? "04b300" : "00ff00";
    }

}
