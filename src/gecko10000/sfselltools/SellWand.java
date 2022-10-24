package gecko10000.sfselltools;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class SellWand extends SlimefunItem implements Rechargeable {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

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
        Player player = e.getPlayer();
        Block block = e.getClickedBlock().orElse(null);
        if (block == null) return;
        if (!Slimefun.getProtectionManager().hasPermission(player, block, Interaction.INTERACT_BLOCK)) return;
        SlimefunItem sfBlock = BlockStorage.check(block);
        // vanilla inventories (we get the block instead of the inventory because we don't want to sell on inventory-less SF blocks)
        if (sfBlock == null) {
            if (!(block.getState() instanceof InventoryHolder holder)) return;
            e.cancel();
            vanillaSell(player, e.getItem(), holder.getInventory());
            return;
        }
        // TODO: selling SF containers?
    }

    private void vanillaSell(Player player, ItemStack wand, Inventory inv) {
        int totalAmount = 0;
        double totalPrice = 0;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) continue;
            ItemStack clone = item.clone();
            clone.setAmount(1);
            Double price = SFSellTools.get().getPriceProvider().getPrice(player, clone);
            if (price == null || price < 0) continue;
            int amount = item.getAmount();
            if (!removeItemCharge(wand, (float) (perItem * amount))) break;
            double toDeposit = price * amount;
            if (SFSellTools.get().deposit(player, toDeposit)) {
                totalAmount += amount;
                inv.setItem(i, null);
                totalPrice += toDeposit;
            }
        }
        player.sendMessage(miniMessage.deserialize("<dark_green>You sold <green><amount></green> items for <green>$<price></green>.", Placeholder.unparsed("amount", totalAmount + ""), Placeholder.unparsed("price", Utils.formatMoney(totalPrice) + "")));
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return (float) maxCharge;
    }
}
