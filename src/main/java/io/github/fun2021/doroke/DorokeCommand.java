package io.github.fun2021.doroke;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DorokeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("doroke.gamemaster")) {
            sender.sendMessage("パーミッションをもっていないためこのコマンドを実行できません");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("/doroke start: ドロケーを開始します");
            sender.sendMessage("/doroke select [player]: ケーサツを選びます");
            return true;
        }

        switch (args[0]) {
            case "start":
                start(sender, command, label, args);
                break;
            case "select":
                select(sender, command, label, args);
                break;
            case "list":
                list(sender, command, label, args);
                break;
            case "reset":
                reset(sender, command, label, args);
                break;
        }

        return true;
    }

    private void reset(CommandSender sender, Command command, String label, String[] args) {
        DorokeGameManager gameManager = Doroke.getInstance().gameManager;

        if (gameManager.getPhase() != DorokeGameManager.Phase.BEFORE_GAME) {
            sender.sendMessage(ChatColor.RED + "ゲームが始まっているため、リセットできません");
            return;
        }

        gameManager.reset();
    }

    private void list(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("---");
        if (args.length < 2) {
            sender.sendMessage("ケーサツ:");
            Doroke.getInstance().keisatsu.forEach(p -> sender.sendMessage("  " + p.getName()));
            sender.sendMessage("---");
            sender.sendMessage("ドロボー:");
            Doroke.getInstance().dorobou.forEach(p -> sender.sendMessage("  " + p.getName()));
        } else {
            switch (args[1]) {
                case "keisatsu":
                    sender.sendMessage("ケーサツ:");
                    Doroke.getInstance().keisatsu.forEach(p -> sender.sendMessage("  " + p.getName()));
                    break;
                case "dorobou":
                    sender.sendMessage("ドロボー:");
                    Doroke.getInstance().dorobou.forEach(p -> sender.sendMessage("  " + p.getName()));
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "指定されたロールは存在しません");
            }
        }
        sender.sendMessage("---");
    }

    private void start(CommandSender sender, Command command, String label, String[] args) {
        DorokeGameManager gameManager = Doroke.getInstance().gameManager;

        if (gameManager.getPhase() != DorokeGameManager.Phase.BEFORE_GAME) {
            sender.sendMessage("ゲームは既に始まっています");
            return;
        }

        gameManager.prepare();
    }

    private void select(CommandSender sender, Command command, String label, String[] args) {
        final Player selected;

        if (args.length == 2) {
            selected = Bukkit.getPlayer(args[1]);
        } else {
            selected = selectKeisatsu();
        }

        if (Objects.isNull(selected)) {
            sender.sendMessage(ChatColor.RED + "候補がいません");
            return;
        }

        Doroke main = Doroke.getInstance();

        selected.teleport(main.configData.keisatsuStayLocation);

        main.keisatsu.add(selected);

        selected.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        selected.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        selected.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        selected.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

        Doroke.broadcastMessage("ケーサツに" + selected.getName() + "さんが追加されました");
    }

    private Player selectKeisatsu() {
        List<Player> candicates = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            Block block1 = player.getLocation().clone().subtract(0, 1, 0).getBlock();
            Block block2 = player.getLocation().clone().subtract(0, 2, 0).getBlock();

            if (block1.getType() == Material.LAPIS_BLOCK &&
                    block2.getType() == Material.LAPIS_BLOCK) {
                candicates.add(player);
            }
        }

        if (candicates.isEmpty()) {
            return null;
        }

        return candicates.get((int) (Math.random() * candicates.size()));
    }

}
