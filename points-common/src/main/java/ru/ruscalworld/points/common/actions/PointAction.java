package ru.ruscalworld.points.common.actions;

import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Messages;
import ru.ruscalworld.points.common.util.Permission;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.exceptions.NotFoundException;

public abstract class PointAction implements Action {
    private final InputType inputType;
    private final String input;
    private Point point;

    protected PointAction(String input, InputType inputType) {
        this.inputType = inputType;
        this.input = input;
    }

    protected PointAction(String slug) {
        this.inputType = InputType.SLUG;
        this.input = slug;
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        this.ensureCanView(executor);
    }

    public final void ensureCanView(CommandExecutor executor) throws ActionException {
        new Permission("view").ensureHas(executor);

        Point point = this.getPoint();
        if (point.isPrivate() && !point.isOwner(executor)) {
            new Permission("view.private").ensureHas(executor);
        }
    }

    public final void ensureCanManage(CommandExecutor executor) throws ActionException {
        new Permission("manage").ensureHas(executor);

        Point point = this.getPoint();
        if (!point.isOwner(executor)) {
            new Permission("manage.others").ensureHas(executor);
        }
    }

    public @NotNull Point getPoint() throws ActionException {
        if (this.getInputType() == InputType.NAME) {
            return this.getPointByName();
        } else return this.getPointBySlug();
    }

    private @NotNull Point getPointBySlug() throws ActionException {
        return this.getPoint("slug");
    }

    private @NotNull Point getPointByName() throws ActionException {
        return this.getPoint("name");
    }

    private @NotNull Point getPoint(String field) throws ActionException {
        if (this.point != null) return point;
        Storage storage = Points.getInstance().getStorage();

        try {
            Point point = storage.find(Point.class, field, this.getInput());
            this.setPoint(point);
            return point;
        } catch (NotFoundException exception) {
            throw new ActionException(Messages.pointNotFound(exception.getKeyValue().toString()));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Messages.unableToRetrieve());
        }
    }

    protected void setPoint(Point point) {
        this.point = point;
    }

    public String getInput() {
        return input;
    }

    public InputType getInputType() {
        return inputType;
    }

    public enum InputType {
        SLUG, NAME
    }
}
