package ch.supsi.minesweeper.service;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.supsi.minesweeper.model.Constant.*;

@Getter
public class UserPreferences {
    public static UserPreferences self;
    private int bombs;
    private String language;
    @Setter private ResourceBundle bundle;
    @Setter private NotificationHandler notificationHandler;

    public static UserPreferences getInstance() {
        if (self == null) self = new UserPreferences();
        return self;
    }

    public void openPreferencesFile() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(new File(CONFIG_PATH.toUri()));
            } catch (IOException e) {
                System.out.println("Cannot open preferences");
            }
        }
    }

    public void init() {
        if (Files.exists(CONFIG_PATH)) {
            Toml toml = new Toml().read(CONFIG_PATH.toFile());
            Long bombValue = toml.getLong("bombs");
            String languageValue = toml.getString("language");
            if (languageValue == null || languageValue.isEmpty()) {
                notifyInvalidLanguage("language_missing");
                return;
            }
            if (!languageValue.equals("en") && !languageValue.equals("it")) {
                notifyInvalidLanguage("language_not_valid");
                return;
            }
            this.language = languageValue;
            this.bundle = ResourceBundle.getBundle("messages", new Locale(this.language));
            if (bombValue == null) {
                notifyInvalidBombs("bombs_missing");
                return;
            }
            if (bombValue <= 0 || bombValue > 80) {
                notifyInvalidBombs("bombs_out_of_range");
                return;
            }
            this.bombs = bombValue.intValue();
        } else {
            createDefaultPreferences();
            this.bombs = DEFAULT_BOMBS;
            this.language = DEFAULT_LANGUAGE;
            this.notificationHandler.notifyInfo(null);
        }
    }

    private UserPreferences() {
        if (Files.exists(CONFIG_PATH)) {
            String tmp = new Toml().read(CONFIG_PATH.toFile()).getString("language");
            this.language = tmp != null ? tmp : DEFAULT_LANGUAGE;
        } else {
            this.language = DEFAULT_LANGUAGE;
        }
    }

    private void createDefaultPreferences() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            HashMap<String, Object> config = new HashMap<>();
            config.put("bombs", DEFAULT_BOMBS);
            config.put("language", DEFAULT_LANGUAGE);
            new TomlWriter().write(config, CONFIG_PATH.toFile());
        } catch (IOException e) {
            System.err.println("Error creating configuration file");
        }
    }

    private void notifyInvalidBombs(String messageKey) {
        String translatedMsg = bundle.getString(messageKey);
        this.bombs = DEFAULT_BOMBS;
        this.notificationHandler.notifyWarning(translatedMsg);
    }

    private void notifyInvalidLanguage(String messageKey) {
        String translatedMsg = bundle.getString(messageKey);
        this.language = DEFAULT_LANGUAGE;
        this.bundle = ResourceBundle.getBundle("messages", new Locale(this.language));
        this.notificationHandler.notifyWarning(translatedMsg);
    }
}
