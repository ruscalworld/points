package ru.ruscalworld.points.common;

import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.translation.GlobalTranslator;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.util.Translator;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.impl.SQLiteStorage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.UUID;
import java.util.function.BiConsumer;

public class Points {
    private static Points instance;

    private final Storage storage;
    private final BiConsumer<Action, CommandExecutor> actionDispatcher;

    public Points(Path dataPath, BiConsumer<Action, CommandExecutor> actionDispatcher) {
        this.actionDispatcher = actionDispatcher;

        // noinspection ResultOfMethodCallIgnored
        dataPath.toFile().mkdirs();
        SQLiteStorage storage = new SQLiteStorage("jdbc:sqlite:" + dataPath.resolve("database.db"));
        storage.registerMigration("points");
        storage.registerConverter(UUID.class, (v) -> UUID.fromString(v.toString()));
        storage.registerConverter(boolean.class, (v) -> v.equals("true"));
        this.storage = storage;
    }

    public boolean initialize() {
        try {
            this.getStorage().actualizeStorageSchema();

            InputStream enLang = this.getClass().getClassLoader().getResourceAsStream("lang/en.properties");
            Properties messages = new Properties();
            messages.load(enLang);
            GlobalTranslator.get().addSource(new Translator(messages, messages));
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
