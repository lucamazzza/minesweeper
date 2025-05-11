package ch.supsi.minesweeper.service;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import static ch.supsi.minesweeper.model.Constant.*;

public class UserPreferences {
    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".minesweeper", "config.toml");

    private int bombs;

    public UserPreferences() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                Toml toml = new Toml().read(CONFIG_PATH.toFile());
                Long bombValue = toml.getLong("bombs");

                if (bombValue == null) {
                    throw new IllegalArgumentException("Valore 'bombs' mancante.");
                }

                if (bombValue <= 0 || bombValue > 80) {
                    throw new IllegalArgumentException("Valore 'bombs' non puo' essere nullo o maggiore di 0.");
                }

                this.bombs = bombValue.intValue();
            } catch (Exception e) {
                System.err.println("Configurazione invalida o mancante. Uso valore di default. Errore: " + e.getMessage());
                this.bombs = DEFAULT_BOMBS;
                notifyUserInvalidConfig();
            }
        } else {
            createDefaultPreferences();
            this.bombs = DEFAULT_BOMBS;
            notifyUserConfigCreated();
        }
    }

    private void createDefaultPreferences() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            var config = new java.util.HashMap<String, Object>();
            config.put("bombs", DEFAULT_BOMBS);

            new TomlWriter().write(config, CONFIG_PATH.toFile());
        } catch (IOException e) {
            System.err.println("Errore nella creazione del file di configurazione.");
        }
    }

    private void notifyUserConfigCreated() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Configuration Created");
            alert.setHeaderText("User Configuration Initialized");
            alert.setContentText("The configuration file was missing and has now been created at:\n" + CONFIG_PATH.toAbsolutePath()
            + " with a default value of" + DEFAULT_BOMBS + " bombs");
            alert.showAndWait();
        });
    }

    private void notifyUserInvalidConfig() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Configuration");
            alert.setHeaderText("Invalid or Missing 'bombs' Value");
            alert.setContentText("The configuration file had an invalid or missing 'bombs' value.\nDefault values will be used.");
            alert.showAndWait();
        });
    }


    public int getBombs() {
        return bombs;
    }
}
