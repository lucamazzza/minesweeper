package ch.supsi.minesweeper.service;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
<<<<<<< HEAD
import javafx.stage.Stage;
=======
>>>>>>> save/load
import lombok.Getter;

import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.supsi.minesweeper.model.Constant.*;

@Getter
public class UserPreferences {
    public static UserPreferences self;

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".minesweeper", "config.toml");
    private static final Path DEFAULT_SAVE_FOLDER = CONFIG_PATH.getParent().resolve("saves");

    private int bombs;
<<<<<<< HEAD
    private String language;
    private ResourceBundle messages;
    private static UserPreferences self;

    public static UserPreferences getInstance() {
        if (self == null) self = new UserPreferences();
        return self;
    }

    private UserPreferences() {
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
=======
    private Path savePath;

    public static UserPreferences getInstance() {
        if (self == null) {
            self = new UserPreferences();
        }
        return self;
    }

    public UserPreferences() {
        preferencesConfiguration();
        registerBombs();
        registerSavePath();
    }

    private void registerBombs() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                Toml toml = new Toml().read(CONFIG_PATH.toFile());
                Long bombValue = toml.getLong("bombs");

                if (bombValue == null) {
                    throw new IllegalArgumentException("Value 'bombs' missing");
                }

                if (bombValue < MIN_BOMBS || bombValue > MAX_BOMBS) {
                    throw new IllegalArgumentException("Value 'bombs' can not be less then 1 or bigger then 80");
                }

                this.bombs = bombValue.intValue();
            } catch (Exception e) {
                this.bombs = DEFAULT_BOMBS;
                String errMsg = "Using default value for 'bombs'(" + DEFAULT_BOMBS + ").\nError: " + e.getMessage();
                System.err.println("Invalid configuration\n" + errMsg);
                notifyUserInvalidConfig("Failed to read 'bombs' value from config", errMsg);
            }
        }
    }

    private void registerSavePath() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                Toml toml = new Toml().read(CONFIG_PATH.toFile());
                String savePath = toml.getString("save-path");

                if (savePath == null) {
                    throw new IllegalArgumentException("Value 'save-path' missing");
                }
                Path path = Path.of(savePath);
                if (!Files.exists(path)) {
                    throw new IllegalArgumentException("Value 'save-path' is not a valid path or directory");
                }

                this.savePath = path;
            } catch (Exception e) {
                String errMsg = "Error: " + e.getMessage();
                System.err.println("Invalid configuration\n" + errMsg);
                notifyUserInvalidConfig("Failed to read 'save-path' value from config", errMsg);
            }
        }
    }

    private void preferencesConfiguration() {
        Path gameFolder = CONFIG_PATH.getParent();

        if (!Files.exists(gameFolder)){
            try {
                Files.createDirectory(gameFolder);
            } catch (IOException e) {
                System.err.println("Error: Failed to create game folder");
                return;
            }
        }

        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);

                var config = new java.util.HashMap<String, Object>();
                config.put("save-path", DEFAULT_SAVE_FOLDER.toString().replaceAll("\\\\", "/"));
                config.put("bombs", DEFAULT_BOMBS);

                new TomlWriter().write(config, CONFIG_PATH.toFile());
                notifyUserConfigCreated();
            } catch (IOException e) {
                System.err.println("Error: Failed to create config file");
            }
>>>>>>> save/load
        }
    }

    private void notifyUserConfigCreated() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
<<<<<<< HEAD
            alert.setTitle(messages.getString("config_created_title"));
            alert.setHeaderText(messages.getString("config_created_header"));
            String content = MessageFormat.format(
                    messages.getString("config_created_message"),
                    CONFIG_PATH.toAbsolutePath(),
                    DEFAULT_BOMBS,
                    DEFAULT_LANGUAGE
            );
            alert.setContentText(content);
            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
=======
            alert.setTitle("Configuration Created");
            alert.setHeaderText("User Configuration Initialized");
            alert.setContentText("The configuration file was missing and has now been created at:\n" + CONFIG_PATH.toAbsolutePath()
            + " with a default value of " + DEFAULT_BOMBS + " bombs");
>>>>>>> save/load
            alert.showAndWait();
        });
    }

<<<<<<< HEAD
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
            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
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
=======
    private void notifyUserInvalidConfig(String header, String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Configuration");
            alert.setHeaderText(header);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }
>>>>>>> save/load
}
