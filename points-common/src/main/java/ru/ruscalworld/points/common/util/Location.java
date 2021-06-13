package ru.ruscalworld.points.common.util;

import com.flowpowered.math.vector.Vector3d;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

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

    public Vector3d getLocation() {
        return new Vector3d(x, y, z);
    }

    public Component getCoordinatesComponent(Style contrast) {
        return Component.text(this.getX(), contrast)
                .append(Component.text(" "))
                .append(Component.text(this.getY(), contrast))
                .append(Component.text(" "))
                .append(Component.text(this.getZ(), contrast));
    }
}
