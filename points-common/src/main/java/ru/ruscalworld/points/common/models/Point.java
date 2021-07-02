package ru.ruscalworld.points.common.models;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.core.*;
import ru.ruscalworld.points.common.util.Location;
import ru.ruscalworld.points.common.util.Slug;
import ru.ruscalworld.points.common.util.Styles;
import ru.ruscalworld.storagelib.DefaultModel;
import ru.ruscalworld.storagelib.annotations.DefaultGenerated;
import ru.ruscalworld.storagelib.annotations.Model;
import ru.ruscalworld.storagelib.annotations.Property;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
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

    public Component getDisplayName(Style contrast) {
        return this.getDisplay(this.getName(), contrast);
    }

    public Component getDisplaySlug(Style contrast) {
        return this.getDisplay(this.getSlug(), contrast);
    }

    private Component getDisplay(String text, Style contrast) {
        Points points = Points.getInstance();
        TextComponent br = Component.text("\n");
        PlayerManager playerManager = points.getPlayerManager();
        OfflinePlayer owner = playerManager.getOfflinePlayer(this.getOwnerID());
        String ownerName = owner != null ? owner.getName() : "Unknown";
        SimpleDateFormat dateFormat = points.getMainConfig().getDateFormat();

        TranslatableComponent hint = Component.translatable(
                "point.info.name", Styles.main(),
                Component.text(this.getName(), Styles.contrast())
        ).append(br).append(Component.translatable(
                "point.info.slug", Styles.main(),
                Component.text(this.getSlug(), Styles.contrast())
        )).append(br).append(Component.translatable(
                "point.info.owner", Styles.main(),
                Component.text(ownerName, Styles.contrast())
        )).append(br).append(Component.translatable(
                "point.info.location", Styles.main(),
                this.getLocation().getCoordinatesComponent(Styles.contrast()),
                Component.text(this.getLocation().getWorldName(), Styles.contrast())
        )).append(br).append(Component.translatable(
                "point.info.created", Styles.main(),
                Component.text(dateFormat.format(this.getCreatedAt()), Styles.contrast())
        ));

        return Component.text(text, contrast)
                .hoverEvent(HoverEvent.showText(hint))
                .clickEvent(ClickEvent.runCommand("/where " + this.getName()));
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
