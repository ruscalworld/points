package ru.ruscalworld.points;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ruscalworld.points.commands.DeletePointCommand;
import ru.ruscalworld.points.commands.SetPointCommand;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.impl.BukkitActionDispatcher;

public final class PointsSpigot extends JavaPlugin {
    private static PointsSpigot instance;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        this.setAdventure(BukkitAudiences.create(this));
        Points points = new Points(this.getDataFolder().toPath(), new BukkitActionDispatcher());
        if (!points.initialize()) this.setEnabled(false);

        PluginCommand setpoint = this.getCommand("setpoint");
        assert setpoint != null;
        setpoint.setExecutor(new SetPointCommand());

        PluginCommand delpoint = this.getCommand("delpoint");
        assert delpoint != null;
        delpoint.setExecutor(new DeletePointCommand());

        instance = this;
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
