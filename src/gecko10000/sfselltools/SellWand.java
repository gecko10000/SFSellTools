package gecko10000.sfselltools;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class SellWand extends SlimefunItem implements Rechargeable {

    private final double maxCharge;
    private final double perItem;

    public SellWand(ItemGroup itemGroup, SlimefunItemStack item, ItemStack[] recipe, double maxCharge, double perItem) {
        super(itemGroup, item, RecipeType.MAGIC_WORKBENCH, recipe);
        this.maxCharge = maxCharge;
        this.perItem = perItem;
        this.register(SFSellTools.get());
    }

    @Override
    public void preRegister() {
        addItemHandler((ItemUseHandler) this::handle);
    }

    private void handle(PlayerRightClickEvent e) {
        Block b = e.getClickedBlock().orElse(null);
        if (b == null) return;
        SlimefunItem block = BlockStorage.check(b);
        if (block == null) {
            if (!(b.getState() instanceof InventoryHolder holder)) return;
            e.cancel();
            vanillaSell(e.getPlayer(), e.getItem(), holder.getInventory());
        }
    }

    private void vanillaSell(Player player, ItemStack wand, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) continue;
            // TODO: commented
            double price = 0.1;//sellPrice(player, item);
            if (price < 0) continue;
            int amount = item.getAmount();
            if (!removeItemCharge(wand, (float) (perItem * amount))) break;
            //depositMoney(player, price * amount);
            inv.setItem(i, null);
        }
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return (float) maxCharge;
    }
}
