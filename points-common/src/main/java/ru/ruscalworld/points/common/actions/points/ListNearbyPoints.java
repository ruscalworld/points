package ru.ruscalworld.points.common.actions.points;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.PlayerAction;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Location;
import ru.ruscalworld.points.common.util.Styles;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.builder.expressions.Comparison;
import ru.ruscalworld.storagelib.builder.expressions.Condition;

import java.util.ArrayList;
import java.util.List;

public class ListNearbyPoints extends PlayerAction {
    private final static int SEARCH_RADIUS = 500;

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Storage storage = Points.getInstance().getStorage();
        Player player = this.getPlayer(executor);
        Location location = player.getLocation();
        List<Point> points = new ArrayList<>();

        try {
            points.addAll(storage.findAll(Point.class, Condition.and(
                    Condition.and(Comparison.lessThan("x", location.getX() + SEARCH_RADIUS), Comparison.biggerThan("x", location.getX() - SEARCH_RADIUS)),
                    Condition.and(Comparison.lessThan("z", location.getZ() + SEARCH_RADIUS), Comparison.biggerThan("z", location.getZ() - SEARCH_RADIUS))
            )));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (points.size() == 0) return Component.translatable("point.no-nearby");

        Component component = Component.empty();
        int count = 0;

        for (Point point : points) {
            if (point.isPrivate() && !point.isOwner(executor)) continue;
            count++;
            component = component.append(Component.text("\n")).append(Component.translatable(
                    "point.nearby.entry", Styles.main(),
                    Component.text(point.getName(), Styles.contrast()),
                    Component.text(Math.round(point.getLocation().getDistance(player.getLocation())) + "m", Styles.contrast())
            ));
        }

        return Component.translatable(
                "point.nearby", Styles.main(),
                Component.text(count, Styles.contrast())
        ).append(component);
    }
}
