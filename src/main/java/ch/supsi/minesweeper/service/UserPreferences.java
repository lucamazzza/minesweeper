package ch.supsi.minesweeper.service;

import ch.supsi.minesweeper.exception.InvalidBombsException;
import ch.supsi.minesweeper.exception.InvalidLanguageException;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.supsi.minesweeper.model.Constant.*;

public class UserPreferences {
    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".minesweeper", "config.toml");

    private int bombs;
    private String language;
    private ResourceBundle messages;

    public UserPreferences() {
        this.language = DEFAULT_LANGUAGE;
        this.messages = ResourceBundle.getBundle("messages", new Locale(this.language));

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
            this.messages = ResourceBundle.getBundle("messages", new Locale(this.language));

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
            this.messages = ResourceBundle.getBundle("messages", new Locale(this.language));
            notifyUserConfigCreated();
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

    private void notifyUserConfigCreated() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(messages.getString("config_created_title"));
            alert.setHeaderText(messages.getString("config_created_header"));

            String content = MessageFormat.format(
                    messages.getString("config_created_message"),
                    CONFIG_PATH.toAbsolutePath(),
                    DEFAULT_BOMBS,
                    DEFAULT_LANGUAGE
            );

            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void notifyUserInvalidConfig(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(messages.getString("invalid_config_title"));
            alert.setHeaderText(messages.getString("invalid_config_header"));

            String content = MessageFormat.format(
                    messages.getString("invalid_config_message"),
                    errorMessage
            );

            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void notifyInvalidBombs(String messageKey) {
        String translatedMsg = messages.getString(messageKey);
        this.bombs = DEFAULT_BOMBS;
        notifyUserInvalidConfig(translatedMsg);
    }

    private void notifyInvalidLanguage(String messageKey) {
        String translatedMsg = messages.getString(messageKey);
        this.language = DEFAULT_LANGUAGE;
        this.messages = ResourceBundle.getBundle("messages", new Locale(this.language));
        notifyUserInvalidConfig(translatedMsg);
    }

    public int getBombs() {
        return bombs;
    }

    public String getLanguage() {
        return language;
    }

    public ResourceBundle getMessages() {
        return messages;
    }
}
