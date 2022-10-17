package gecko10000.sfselltools.prices;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PriceProvider {

    // both of these functions get the price for a single item.
    public abstract Double getPrice(Player player, ItemStack item);
    public abstract Double getPrice(OfflinePlayer player, ItemStack item);

}
