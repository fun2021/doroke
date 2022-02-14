package io.github.fun2021.doroke;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public final class DorobouListener implements Listener {

    private final DorokeGameManager gameManager;
    private final Set<Player> dorobou;

    public DorobouListener() {
        final Doroke main = Doroke.getInstance();
        gameManager = main.gameManager;
        dorobou = main.dorobou;
    }

    @EventHandler
    public void onOpenChest(InventoryOpenEvent event) {
        if (gameManager.getPhase() == DorokeGameManager.Phase.BEFORE_GAME) { // It is in Game
            return;
        }

        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof Chest)) {
            return;
        }

        inventory.clear();

        final Player player = (Player) event.getPlayer();

        if (!dorobou.contains(player)) {
            return;
        }

        int chance = 3;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (Math.random() < 0.0363) {
                inventory.setItem(i, new ItemStack(Material.COOKED_BEEF));
                chance--;
                if (chance == 0) break;
            }
        }
    }

}