package ru.ruscalworld.points.common;

import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.impl.SQLiteStorage;

import java.util.UUID;

public class Points {
    private static Points instance;

    private final Storage storage;

    public Points(String dataPath) {
        SQLiteStorage storage = new SQLiteStorage("jdbc:sqlite:" + dataPath);
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

        return true;
    }

    public static Points getInstance() {
        return instance;
    }

    public Storage getStorage() {
        return storage;
    }
}
