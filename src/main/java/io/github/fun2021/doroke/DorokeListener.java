package io.github.fun2021.doroke;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

public class DorokeListener implements Listener {

    private final DorokeGameManager gameManager;
    private final Set<Player> dorobou;
    private final Set<Player> keisatsu;

    public DorokeListener() {
        final Doroke main = Doroke.getInstance();
        gameManager = main.gameManager;
        dorobou = main.dorobou;
        keisatsu = main.keisatsu;
    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (gameManager.getPhase() != DorokeGameManager.Phase.BEFORE_GAME) {
            return;
        }

        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            return;
        }

        Player attackee = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if (dorobou.contains(attackee) && keisatsu.contains(attacker)) {
            attackee.setHealth(0);
        }
    }

}
