package gecko10000.sfselltools.prices;

import me.gypopo.economyshopgui.api.EconomyShopGUIHook;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EconomyShopGUIProvider extends PriceProvider {
    @Override
    public Double getPrice(Player player, ItemStack item) {
        return EconomyShopGUIHook.getItemSellPrice(player, item);
    }

    @Override
    public Double getPrice(OfflinePlayer player, ItemStack item) {
        return EconomyShopGUIHook.getItemSellPrice(item);
    }
}
