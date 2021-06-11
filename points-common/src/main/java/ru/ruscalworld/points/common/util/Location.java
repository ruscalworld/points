package ru.ruscalworld.points.common.util;

public class Location {
    private final int x;
    private final int y;
    private final int z;
    private final String worldName;

    public Location(int x, int y, int z, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getWorldName() {
        return worldName;
    }
}
