package co.valdeon.Tribes.util;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public enum Direction {

    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W"),
    NORTHWEST("NW"),
    NORTHEAST("NE"),
    SOUTHWEST("SW"),
    SOUTHEAST("SE");

    String symbol;

    Direction(String s) {
        this.symbol = s;
    }

    public String toSymbol() {
        return this.symbol;
    }

    public static Direction getFromYaw(Location l) {
        double rotation = (l.getYaw() - 90) % 360;

        if (rotation < 0) {
            rotation += 360.0;
        }

        if (0 <= rotation && rotation < 22.5)
            return NORTH;
        else if (22.5 <= rotation && rotation < 67.5)
            return NORTHEAST;
        else if (67.5 <= rotation && rotation < 112.5)
            return EAST;
        else if (112.5 <= rotation && rotation < 157.5)
            return SOUTHEAST;
        else if (157.5 <= rotation && rotation < 202.5)
            return SOUTH;
        else if (202.5 <= rotation && rotation < 247.5)
            return SOUTHWEST;
        else if (247.5 <= rotation && rotation < 292.5)
            return WEST;
        else if (292.5 <= rotation && rotation < 337.5)
            return NORTHWEST;
        else if (337.5 <= rotation && rotation < 360.0)
            return NORTH;
        else
            return null;
    }

    public static Direction getFromYaw(Player p) {
        return getFromYaw(p.getLocation());
    }

    public static Direction getCardinalFromYaw(Location l) {
        double rotation = l.getYaw();

        if (0 <= rotation && rotation < 45)
            return SOUTH;
        else if (45 <= rotation && rotation < 135)
            return WEST;
        else if (135 <= rotation && rotation < 225)
            return NORTH;
        else if (225 <= rotation && rotation < 315)
            return EAST;
        else if (315 <= rotation && rotation < 360)
            return SOUTH;
        else
            return null;
    }

    public static Direction getCardinalFromYaw(Player p) {
        return getCardinalFromYaw(p.getLocation());
    }

}
