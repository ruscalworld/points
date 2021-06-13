package ru.ruscalworld.points.common.actions.points;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.exceptions.NotAPlayerException;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Messages;
import ru.ruscalworld.points.common.util.Permission;
import ru.ruscalworld.points.common.util.Styles;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.exceptions.NotFoundException;

public class CreatePoint implements Action {
    private final String name;

    public CreatePoint(String name) {
        this.name = name;
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Storage storage = Points.getInstance().getStorage();
        Player player = (Player) executor;
        Point point = new Point(this.getName(), player.getUUID(), player.getLocation());
        boolean found;

        try {
            storage.find(Point.class, "slug", point.getSlug());
            found = true;
        } catch (NotFoundException exception) {
            found = false;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Messages.unableToRetrieve());
        }

        if (found) throw new ActionException(Component.translatable("errors.point.similar", Styles.main()));

        try {
            storage.save(point);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Component.translatable(
                    "errors.point.create", Styles.main()
            ));
        }

        return Component.translatable(
                "point.create.success", Styles.main(),
                Component.text(point.getSlug(), Styles.contrast())
        );
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        new Permission("create").ensureHas(executor);
        if (executor instanceof Player) return;
        throw new NotAPlayerException();
    }

    public String getName() {
        return name;
    }
}
