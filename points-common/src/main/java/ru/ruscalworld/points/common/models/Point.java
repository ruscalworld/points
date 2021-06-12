package ru.ruscalworld.points.common.models;

import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.Transliterator;
import org.jetbrains.annotations.NotNull;
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

    @Property(column = "createdAt")
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
        isPrivate = aPrivate;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
