package io.github.fun2021.doroke;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Set;

public final class KeisatsuListener implements Listener {

    private final DorokeGameManager gameManager;
    private final Set<Player> keisatsu;

    public KeisatsuListener() {
        Doroke main = Doroke.getInstance();
        gameManager = main.gameManager;
        keisatsu = main.keisatsu;
    }

    @EventHandler
    public void onOpenChest(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();

        if (keisatsu.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (keisatsu.contains(player)) {
            event.setCancelled(true);
        }
    }

}
