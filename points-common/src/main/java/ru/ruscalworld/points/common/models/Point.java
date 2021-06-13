package ru.ruscalworld.points.common.models;

import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.core.WorldMap;
import ru.ruscalworld.points.common.util.Location;
import ru.ruscalworld.points.common.util.Slug;
import ru.ruscalworld.storagelib.DefaultModel;
import ru.ruscalworld.storagelib.annotations.DefaultGenerated;
import ru.ruscalworld.storagelib.annotations.Model;
import ru.ruscalworld.storagelib.annotations.Property;

import java.sql.Timestamp;
import java.util.UUID;

@Model(table = "points")
public class Point extends DefaultModel {
    @Property(column = "name")
    @NotNull
    private String name;

    @Property(column = "slug")
    @NotNull
    private final String slug;

    @Property(column = "owner")
    @NotNull
    private final UUID ownerID;

    @Property(column = "x")
    private int x;
    @Property(column = "y")
    private int y;
    @Property(column = "z")
    private int z;
    @Property(column = "world")
    @NotNull
    private String world;

    @Property(column = "private")
    private boolean isPrivate;

    @Property(column = "hidden")
    private boolean isHidden;

    @Property(column = "created_at")
    @DefaultGenerated
    @NotNull
    private final Timestamp createdAt;

    public Point() {
        this.name = "";
        this.slug = "";
        this.world = "";
        this.ownerID = UUID.randomUUID();
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Point(@NotNull String name, @NotNull UUID ownerID, @NotNull Location location) {
        this.name = name;
        this.slug = Slug.make(this.getName());
        this.ownerID = ownerID;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.isHidden = true;
        this.isPrivate = false;
        this.world = location.getWorldName();
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public @NotNull Location getLocation() {
        return new Location(this.x, this.y, this.z, this.world);
    }

    public void setLocation(@NotNull Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.world = location.getWorldName();
    }

    public boolean isOwner(CommandExecutor executor) {
        if (executor instanceof Player) {
            Player player = (Player) executor;
            return player.getUUID().equals(this.getOwnerID());
        }

        return false;
    }

    public void createMarker() {
        if (this.isHidden()) return;
        WorldMap worldMap = Points.getInstance().getWorldMap();
        worldMap.addMarker(this);
    }

    public void updateMarker() {
        if (this.isHidden()) {
            this.deleteMarker();
            return;
        }

        WorldMap map = Points.getInstance().getWorldMap();
        map.updateMarker(this);
    }

    public void deleteMarker() {
        WorldMap map = Points.getInstance().getWorldMap();
        map.removeMarker(this);
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getSlug() {
        return slug;
    }

    public @NotNull UUID getOwnerID() {
        return ownerID;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        if (aPrivate) this.isHidden = true;
        this.isPrivate = aPrivate;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public @NotNull Timestamp getCreatedAt() {
        return createdAt;
    }
}
