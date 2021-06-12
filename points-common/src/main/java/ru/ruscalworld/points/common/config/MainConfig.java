package ru.ruscalworld.points.common.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;

public class MainConfig {
    private final Properties properties;
    private final File configFile;

    public MainConfig(Path configPath) {
        this.configFile = configPath.resolve("config.properties").toFile();
        this.properties = new Properties();
    }

    public void load() throws IOException {
        if (!this.getConfigFile().exists()) return;
        FileInputStream stream = new FileInputStream(this.getConfigFile());
        this.getProperties().load(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    public String getLanguage() {
        return this.getProperties().getProperty("language", "en");
    }

    public Properties getProperties() {
        return properties;
    }

    public File getConfigFile() {
        return configFile;
    }
}
