package me.goarty.moblimit;

import me.goarty.moblimit.spawnlimit.SpawnLimit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobLimit extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new SpawnLimit(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
