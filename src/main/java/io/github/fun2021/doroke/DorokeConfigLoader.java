package io.github.fun2021.doroke;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

class DorokeConfigLoader {

    DorokeConfigData loadConfig() {
        final FileConfiguration config = Doroke.getInstance().getConfig();
        final World world = Bukkit.getWorlds().get(0);
        final List<Location> locations = new ArrayList<>();
        final List<Integer> times = new ArrayList<>();

        try {
            final String[] locationPaths = new String[]{"keisatsu-stay", "dorobou-start", "keisatsu-start"};
            final String[] timePaths = new String[]{"prepare-time", "game-time"};

            for (String locationPath : locationPaths) {
                final double x = config.getDouble(locationPath + ".x");
                final double y = config.getDouble(locationPath + ".y");
                final double z = config.getDouble(locationPath + ".z");
                locations.add(new Location(world, x, y, z));
            }

            for (String timePath : timePaths) {
                final int time = config.getInt(timePath);
                times.add(time);
            }

            return new DorokeConfigData(locations.get(0), locations.get(1), locations.get(2), times.get(0), times.get(1));
        } catch (Exception e) {
            DorokeLogger.info("コンフィグがロードできませんでした");
            DorokeLogger.info("コンフィグが正しく記述されているか確認してください");
            e.printStackTrace();
            return null;
        }
    }

}
