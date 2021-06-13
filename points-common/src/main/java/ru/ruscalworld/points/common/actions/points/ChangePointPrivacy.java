package ru.ruscalworld.points.common.actions.points;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.PointAction;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Messages;
import ru.ruscalworld.points.common.util.Styles;
import ru.ruscalworld.storagelib.Storage;

public class ChangePointPrivacy extends PointAction {
    private final boolean isPrivate;

    public ChangePointPrivacy(String input, boolean isPrivate) {
        super(input);
        this.isPrivate = isPrivate;
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Point point = this.getPoint();
        point.setPrivate(this.isPrivate());

        Storage storage = Points.getInstance().getStorage();
        try {
            storage.save(point);
            point.updateMarker();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Messages.unableToUpdate());
        }

        return Component.translatable(
                point.isPrivate() ? "point.private.on" : "point.private.off", Styles.main(),
                Component.text(point.getName(), Styles.contrast())
        );
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        super.ensureCanExecute(executor);
        this.ensureCanManage(executor);
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
