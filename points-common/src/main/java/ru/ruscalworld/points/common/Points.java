package ru.ruscalworld.points.common;

import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.impl.SQLiteStorage;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Points {
    private static Points instance;

    private final Storage storage;
    private final BiConsumer<Action, CommandExecutor> actionDispatcher;

    public Points(Path dataPath, BiConsumer<Action, CommandExecutor> actionDispatcher) {
        this.actionDispatcher = actionDispatcher;
        SQLiteStorage storage = new SQLiteStorage("jdbc:sqlite:" + dataPath.resolve("database.db"));
        storage.registerMigration("points");
        storage.registerConverter(UUID.class, (v) -> UUID.fromString(v.toString()));
        this.storage = storage;
    }

    public boolean initialize() {
        try {
            this.getStorage().actualizeStorageSchema();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        instance = this;
        return true;
    }

    public static Points getInstance() {
        return instance;
    }

    public Storage getStorage() {
        return storage;
    }

    public BiConsumer<Action, CommandExecutor> getActionDispatcher() {
        return actionDispatcher;
    }
}
