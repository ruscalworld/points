package ru.ruscalworld.points.common.actions.points;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.PlayerAction;
import ru.ruscalworld.points.common.core.ActionException;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.storagelib.Storage;

public class CreatePoint extends PlayerAction {
    private final String name;

    public CreatePoint(String name) {
        this.name = name;
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Storage storage = Points.getInstance().getStorage();
        Player player = (Player) executor;
        Point point = new Point(this.getName(), player.getUUID(), player.getLocation());

        try {
            storage.save(point);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Component.text("Unable to create point"));
        }

        return Component.text("Point successfully created with slug " + point.getSlug());
    }

    public String getName() {
        return name;
    }
}
