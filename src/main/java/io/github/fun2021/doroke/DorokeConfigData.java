package io.github.fun2021.doroke;

import org.bukkit.Location;

class DorokeConfigData {

    final Location keisatsuStayLocation;
    final Location dorobouStartLocation;
    final Location keisatsuStartLocation;
    final int prepareTime;
    final int gameTime;

    DorokeConfigData(Location keisatsuStayLocation, Location dorobouStartLocation, Location keisatsuStartLocation, int prepareTime, int gameTime) {
        this.keisatsuStayLocation = keisatsuStayLocation;
        this.dorobouStartLocation = dorobouStartLocation;
        this.keisatsuStartLocation = keisatsuStartLocation;
        this.prepareTime = prepareTime;
        this.gameTime = gameTime;
    }

}
