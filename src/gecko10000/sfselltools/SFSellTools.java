package gecko10000.sfselltools;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SFSellTools extends JavaPlugin implements SlimefunAddon {

    private static SFSellTools instance;

    public void onEnable() {
        instance = this;
        new ItemManager();
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
