package ru.ruscalworld.points.common.util;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

public class Translator implements net.kyori.adventure.translation.Translator {
    private final Properties fallback;
    private final Properties current;

    public Translator(Properties fallback, Properties current) {
        this.fallback = fallback;
        this.current = current;
    }

    @Override
    public @NotNull Key name() {
        return Key.key("points");
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        String pattern = null;
        if (this.getCurrent().containsKey(key)) pattern = this.getCurrent().getProperty(key);
        else if (this.getFallback().containsKey(key)) pattern = this.getFallback().getProperty(key);
        if (pattern == null) return null;
        return new MessageFormat(pattern, null);
    }

    public Properties getFallback() {
        return fallback;
    }

    public Properties getCurrent() {
        return current;
    }
}
