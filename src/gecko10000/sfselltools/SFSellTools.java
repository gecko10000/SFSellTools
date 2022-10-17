package gecko10000.sfselltools;

import gecko10000.sfselltools.prices.EconomyShopGUIProvider;
import gecko10000.sfselltools.prices.PriceProvider;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SFSellTools extends JavaPlugin implements SlimefunAddon {

    private static SFSellTools instance;
    private Economy economy;
    private PriceProvider priceProvider = null;

    public void onEnable() {
        instance = this;
        new ItemManager();
        loadEconomy();
        loadPriceProvider();
    }
    
    private void loadEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            throw new RuntimeException("Economy plugin not found.");
        this.economy = rsp.getProvider();
    }

    private void loadPriceProvider() {
        if (enabled("EconomyShopGUI") || enabled("EconomyShopGUI-Premium")) {
            priceProvider = new EconomyShopGUIProvider();
        }
    }

    private boolean enabled(String plugin) {
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }

    public boolean deposit(OfflinePlayer player, double amount) {
        return economy.depositPlayer(player, amount).transactionSuccess();
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
