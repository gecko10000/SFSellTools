package gecko10000.sfselltools;

import gecko10000.sfselltools.prices.EconomyShopGUIProvider;
import gecko10000.sfselltools.prices.PriceProvider;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SFSellTools extends JavaPlugin implements SlimefunAddon {

    private static SFSellTools instance;
    private PriceProvider priceProvider = null;

    public void onEnable() {
        instance = this;
        new ItemManager();
        loadPriceProvider();
    }

    private void loadPriceProvider() {
        if (enabled("EconomyShopGUI") || enabled("EconomyShopGUI-Premium")) {
            priceProvider = new EconomyShopGUIProvider();
        }
    }

    private boolean enabled(String plugin) {
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }

    public PriceProvider getPriceProvider() {
        return priceProvider;
    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/gecko10000/SFSellTools/issues";
    }

    public static SFSellTools get() {
        return instance;
    }

}
