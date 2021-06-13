package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.spigot.impl.BukkitPlayer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PointsTabCompleter implements TabCompleter {
    private final ConcurrentLinkedDeque<Point> cachedPoints = new ConcurrentLinkedDeque<>();
    private Timestamp lastUpdated = null;

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) return null;
        BukkitPlayer player = new BukkitPlayer(sender);
        if (this.getLastUpdated() == null || System.currentTimeMillis() - this.getLastUpdated().getTime() > 2 * 1000)
            CompletableFuture.runAsync(this::updateCache);

        switch (command.getLabel()) {
            case "setpoint":
                return new ArrayList<>();
            case "delpoint":
                if (args.length > 1) return new ArrayList<>();
                return this.getOwned(String.join(" ", args), player, true);
            case "getpoint":
                if (args.length > 1) return new ArrayList<>();
                return this.getPublicAndOwned(String.join(" ", args), player);
            case "point":
                if (args.length == 4) return Arrays.asList("true", "false");
                if (args.length != 2) return null;
                return this.getOwned(args[1], player, false);
        }

        return null;
    }

    public void updateCache() {
        synchronized (this.cachedPoints) {
            this.cachedPoints.clear();
        }

        try {
            List<Point> points = Points.getInstance().getStorage().retrieveAll(Point.class);

            synchronized (this.cachedPoints) {
                this.cachedPoints.addAll(points);
                this.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public synchronized List<String> getOwned(String input, BukkitPlayer player, boolean names) {
        List<String> result = new ArrayList<>();

        for (Point point : this.getCachedPoints()) if (point.isOwner(player)) {
            String value = names ? point.getName() : point.getSlug();
            if (value.toLowerCase(Locale.ROOT).startsWith(input.toLowerCase(Locale.ROOT))) result.add(value);
        }

        return result;
    }

    public synchronized List<String> getPublicAndOwned(String input, BukkitPlayer player) {
        List<String> result = new ArrayList<>();

        for (Point point : this.getCachedPoints()) if (!point.isPrivate() || point.isOwner(player)) {
            String name = point.getName();
            if (name.toLowerCase(Locale.ROOT).startsWith(input.toLowerCase(Locale.ROOT))) result.add(name);
        }

        return result;
    }

    public synchronized ConcurrentLinkedDeque<Point> getCachedPoints() {
        return cachedPoints;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
