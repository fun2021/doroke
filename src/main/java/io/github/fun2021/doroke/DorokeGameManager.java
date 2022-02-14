package io.github.fun2021.doroke;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class DorokeGameManager {

    private DorokeTimer timer;
    private final DorokeConfigData configData;
    private Phase phase;
    private final Set<Player> keisatsu;
    private final Set<Player> dorobou;
    DorokeGameManager() {
        phase = Phase.BEFORE_GAME;
        configData = Doroke.getInstance().configData;

        keisatsu = Doroke.getInstance().keisatsu;
        dorobou = Doroke.getInstance().dorobou;
    }

    public Phase getPhase() {
        return phase;
    }

    /**
     * This method must be called by AooniCommand
     */
    void prepare() {

        Bukkit.getOnlinePlayers().stream().filter(p -> !keisatsu.contains(p)).forEach(p -> dorobou.add(p));

        final Location destination = configData.dorobouStartLocation;
        dorobou.forEach(p -> p.teleport(destination));

        new BukkitRunnable() {
            public void run() {
                start(); // Run after prepare time
            }
        }.runTaskLater(Doroke.getInstance(), configData.prepareTime);

        phase = Phase.PREPARING;

        for (Listener listener : Doroke.getInstance().listeners) {
            Bukkit.getPluginManager().registerEvents(listener, Doroke.getInstance());
        }
    }

    private void start() {
        final Location destination = configData.keisatsuStartLocation;
        keisatsu.forEach(p -> p.teleport(destination));

        timer = new DorokeTimer(this, configData.gameTime);
        timer.start();

        phase = Phase.INGAME;
    }

    /**
     * This method must be called by AooniGameTimer
     */
    void end() {
        phase = Phase.BEFORE_GAME;
        for (Listener listener : Doroke.getInstance().listeners) {
            HandlerList.unregisterAll(listener);
        }
    }

    public void reset() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.getInventory().clear();
            p.setHealth(0);
        });
        keisatsu.clear();
        dorobou.clear();
    }

    public enum Phase {
        BEFORE_GAME,
        PREPARING,
        INGAME,
    }

}
