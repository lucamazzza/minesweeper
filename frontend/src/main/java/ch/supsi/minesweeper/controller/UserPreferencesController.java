package ch.supsi.minesweeper.controller;

import ch.supsi.minesweeper.service.NotificationHandler;
import ch.supsi.minesweeper.service.UserPreferences;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import static ch.supsi.minesweeper.model.Constant.*;

public class UserPreferencesController implements NotificationHandler {
    private static UserPreferencesController self;
    @Setter private ResourceBundle bundle;

    private UserPreferencesController() {
    }

    public static UserPreferencesController getInstance() {
        if (self == null) self = new UserPreferencesController();
        return self;
    }
    @Override
    public void notifyInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("config_created_title"));
            alert.setHeaderText(bundle.getString("config_created_header"));
            String content = MessageFormat.format(
                    bundle.getString("config_created_message"),
                    CONFIG_PATH.toAbsolutePath(),
                    DEFAULT_BOMBS,
                    DEFAULT_LANGUAGE
            );
            alert.setContentText(content);
            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
            alert.showAndWait();
        });
    }

    @Override
    public void notifyWarning(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("invalid_config_title"));
            alert.setHeaderText(bundle.getString("invalid_config_header"));
            String content = MessageFormat.format(
                    bundle.getString("invalid_config_message"),
                    message
            );
            alert.setContentText(content);
            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
            alert.showAndWait();
        });
    }

    @Override
    public void notifyError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("unknown_error"));
            alert.setHeaderText(bundle.getString("unknown_error_occourred"));
            String content = MessageFormat.format(
                    bundle.getString("close_app"),
                    message
            );
            alert.setContentText(content);

            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
            alert.showAndWait();
        });
    }
}
