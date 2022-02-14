package io.github.fun2021.doroke;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class DorokeTimer extends BukkitRunnable {

    private final DorokeGameManager parent;
    int remain;

    public DorokeTimer(DorokeGameManager dorokeGameManager, int time) {
        parent = dorokeGameManager;
        remain = time;
    }

    @Override
    public void run() {
        remain--;
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent("残り時間: " + remain + "秒")
            );
        });

        if (remain == 0) {
            end();
        } else if (remain <= 3) {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.sendTitle(String.valueOf(remain), "", 0, 60, 0);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);
            });
        }
    }

    void start() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendTitle("ドロケー開始", "", 10, 70, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0);
        });
        runTaskTimer(Doroke.getInstance(), 0, 20);
    }

    private void end() {
        cancel();
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendTitle("終了", "", 0, 70, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1, 0);
        });
        parent.end();
    }

}
