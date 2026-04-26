package me.ecocontrol;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        getCommand("ecogui").setExecutor(new CommandHandler());
        getCommand("reload").setExecutor(new CommandHandler());

        getLogger().info("EcoControl Enabled!");
    }

    public static Main getInstance() {
        return instance;
    }
}