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

public class ChangePointVisibility extends PointAction {
    private final boolean hidden;

    public ChangePointVisibility(String input, boolean hidden) {
        super(input);
        this.hidden = hidden;
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Point point = this.getPoint();
        point.setHidden(this.isHidden());

        Storage storage = Points.getInstance().getStorage();
        try {
            storage.save(point);
            point.updateMarker();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Messages.unableToUpdate());
        }

        return Component.translatable(
                point.isHidden() ? "point.hidden.on" : "point.hidden.off", Styles.main(),
                Component.text(point.getName(), Styles.contrast())
        );
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        super.ensureCanExecute(executor);
        this.ensureCanManage(executor);
    }

    public boolean isHidden() {
        return hidden;
    }
}
