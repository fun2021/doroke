package io.github.fun2021.doroke;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class Doroke extends JavaPlugin {

    public Set<Player> dorobou;
    public Set<Player> keisatsu;
    public DorokeGameManager gameManager;

    public DorokeConfigData configData;

    DorokeConfigLoader configLoader;

    public static Doroke getInstance() {
        return (Doroke) Bukkit.getPluginManager().getPlugin("Doroke");
    }

    public static void broadcastMessage(String msg) {
        Bukkit.broadcastMessage(msg);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configLoader = new DorokeConfigLoader();
        configData = configLoader.loadConfig();
        dorobou = new HashSet<>();
        keisatsu = new HashSet<>();
        gameManager = new DorokeGameManager();

        getServer().getPluginManager().registerEvents(new DorobouListener(), this);
        getServer().getPluginManager().registerEvents(new DorokeListener(), this);
        getServer().getPluginManager().registerEvents(new KeisatsuListener(), this);

        getCommand("doroke").setExecutor(new DorokeCommand());
    }

}
