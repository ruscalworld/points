package ru.ruscalworld.points.spigot;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileFormat;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ruscalworld.points.spigot.commands.*;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.spigot.impl.BukkitActionDispatcher;

import java.io.IOException;

public final class PointsSpigot extends JavaPlugin {
    private static PointsSpigot instance;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        this.setAdventure(BukkitAudiences.create(this));
        Points points = new Points(this.getDataFolder().toPath(), new BukkitActionDispatcher());
        if (!points.initialize()) this.setEnabled(false);

        PluginCommand point = this.getCommand("point");
        assert point != null;
        point.setExecutor(new PointCommand());
        point.setTabCompleter(new PointsTabCompleter());

        PluginCommand setpoint = this.getCommand("setpoint");
        assert setpoint != null;
        setpoint.setExecutor(new CreatePointCommand());
        setpoint.setTabCompleter(new PointsTabCompleter());

        PluginCommand delpoint = this.getCommand("delpoint");
        assert delpoint != null;
        delpoint.setExecutor(new DeletePointCommand());
        delpoint.setTabCompleter(new PointsTabCompleter());

        PluginCommand getpoint = this.getCommand("getpoint");
        assert getpoint != null;
        getpoint.setExecutor(new ViewPointCommand());
        getpoint.setTabCompleter(new PointsTabCompleter());

        if (CommodoreProvider.isSupported()) {
            Commodore commodore = CommodoreProvider.getCommodore(this);

            try {
                commodore.register(point, CommodoreFileFormat.parse(this.getResource("commands/point.commodore")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        instance = this;
    }

    @Override
    public void onDisable() {
        this.getAdventure().close();
        this.setAdventure(null);
    }

    public static PointsSpigot getInstance() {
        return instance;
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    private void setAdventure(BukkitAudiences adventure) {
        this.adventure = adventure;
    }
}
