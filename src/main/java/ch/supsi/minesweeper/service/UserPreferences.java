package ch.supsi.minesweeper.service;

import ch.supsi.minesweeper.exception.InvalidBombsException;
import ch.supsi.minesweeper.exception.InvalidLanguageException;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ResourceBundle;

import static ch.supsi.minesweeper.model.Constant.*;

public class UserPreferences {
    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".minesweeper", "config.toml");

    private int bombs;
    private String language;
    private ResourceBundle messages;


    public UserPreferences() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                Toml toml = new Toml().read(CONFIG_PATH.toFile());
                Long bombValue = toml.getLong("bombs");
                String languageValue = toml.getString("language");

                if (languageValue == null || languageValue.isEmpty()) {
                    throw new InvalidLanguageException("'language' value missing");
                }

                if (!languageValue.equals("en") && !languageValue.equals("it")){
                    throw new InvalidLanguageException("'language' not valid" );
                }

                if (bombValue == null) {
                    throw new InvalidBombsException("'bombs' value missing");
                }

                if (bombValue <= 0 || bombValue > 80) {
                    throw new InvalidBombsException("'bombs' value out of range");
                }

                this.bombs = bombValue.intValue();
                this.language = languageValue;
            } catch (Exception e) {
                if (e instanceof InvalidLanguageException) {
                    this.language = DEFAULT_LANGUAGE;
                }

                if (e instanceof InvalidBombsException){
                    this.bombs = DEFAULT_BOMBS;
                }
                notifyUserInvalidConfig(e.getMessage());
            }
        } else {
            createDefaultPreferences();
            this.bombs = DEFAULT_BOMBS;
            this.language = DEFAULT_LANGUAGE;
            notifyUserConfigCreated();
        }
    }

    private void createDefaultPreferences() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            var config = new java.util.HashMap<String, Object>();
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
            alert.setTitle("Configuration Created");
            alert.setHeaderText("User Configuration Initialized");
            alert.setContentText("The configuration file was missing and has now been created at:\n" + CONFIG_PATH.toAbsolutePath()
            + " with a default value of " + DEFAULT_BOMBS + " bombs and the default language " + DEFAULT_LANGUAGE);
            alert.showAndWait();
        });
    }

    private void notifyUserInvalidConfig(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Configuration");
            alert.setHeaderText("Invalid or Missing Value");
            alert.setContentText(errorMessage + " default value will be used");
            alert.showAndWait();
        });
    }


    public int getBombs() {
        return bombs;
    }

    public String getLanguage() {
        return language;
    }
}
