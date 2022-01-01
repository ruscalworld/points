package ru.ruscalworld.points.common;

import de.bluecolored.bluemap.api.BlueMapAPI;
import net.kyori.adventure.translation.GlobalTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ruscalworld.points.common.config.MainConfig;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.PlayerManager;
import ru.ruscalworld.points.common.core.WorldMap;
import ru.ruscalworld.points.common.maps.BlueMap;
import ru.ruscalworld.points.common.maps.NullWorldMap;
import ru.ruscalworld.points.common.util.Translator;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.impl.SQLiteStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;
import java.util.UUID;
import java.util.function.BiConsumer;

public class Points {
    private static final Logger logger = LoggerFactory.getLogger("Points");
    private static Points instance;

    private WorldMap worldMap;
    private final Storage storage;
    private final MainConfig mainConfig;
    private final PlayerManager playerManager;
    private final BiConsumer<Action, CommandExecutor> actionDispatcher;

    public Points(Path dataPath, PlayerManager playerManager, BiConsumer<Action, CommandExecutor> actionDispatcher) {
        this.playerManager = playerManager;
        this.actionDispatcher = actionDispatcher;
        this.mainConfig = new MainConfig(dataPath);

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
            this.getMainConfig().load();

            GlobalTranslator.get().addSource(new Translator(
                    this.getMessages("en"),
                    this.getMessages(this.getMainConfig().getLanguage())
            ));

            this.setWorldMap(new NullWorldMap());
            try {
                BlueMapAPI.onEnable(api -> this.setWorldMap(new BlueMap()));
                BlueMapAPI.onDisable(api -> this.setWorldMap(new NullWorldMap()));
            } catch (NoClassDefFoundError ignored) { }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        instance = this;
        return true;
    }

    protected Properties getMessages(String language) throws IOException {
        InputStream stream = this.getMessagesStream(language);
        if (stream == null) stream = this.getMessagesStream("en");
        InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        Properties messages = new Properties();
        messages.load(reader);
        return messages;
    }

    protected InputStream getMessagesStream(String language) {
        return this.getClass().getClassLoader().getResourceAsStream("lang/" + language + ".properties");
    }

    public static Points getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return logger;
    }

    public Storage getStorage() {
        return storage;
    }

    public BiConsumer<Action, CommandExecutor> getActionDispatcher() {
        return actionDispatcher;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        logger.info("Using {} as world map", worldMap.getClass().getSimpleName());
        this.worldMap = worldMap;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
