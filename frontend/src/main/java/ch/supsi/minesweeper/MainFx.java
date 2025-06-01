package ch.supsi.minesweeper;

import ch.supsi.minesweeper.controller.GameController;
import ch.supsi.minesweeper.controller.UserPreferencesController;
import ch.supsi.minesweeper.model.*;
import ch.supsi.minesweeper.service.NotificationHandler;
import ch.supsi.minesweeper.service.UserPreferences;
import ch.supsi.minesweeper.view.*;
import de.jangassen.MenuToolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.*;

public class MainFx extends Application {
    public static final String APP_TITLE = "minesweeper";
    private final AbstractModel gameModel;
    private final AbstractModel boardModel;
    private final ControlledFxView menuBarView;
    private final ControlledFxView gameBoardView;
    private final UncontrolledFxView userFeedbackView;
    private final EventHandler gameEventHandler;
    private final PlayerEventHandler playerEventHandler;
    private final NotificationHandler notificationHandler;
    private final ResourceBundle bundle;

    public MainFx() {
        UserPreferences preferences = UserPreferences.getInstance();
        this.bundle = ResourceBundle.getBundle("messages", new Locale(preferences.getLanguage()));
        preferences.setBundle(bundle);
        this.notificationHandler = UserPreferencesController.getInstance();
        notificationHandler.setBundle(bundle);
        preferences.setNotificationHandler(this.notificationHandler);
        preferences.init();
        this.gameModel = GameModel.getInstance();
        this.boardModel = BoardModel.getInstance();
        this.menuBarView = MenuBarViewFxml.getInstance(bundle);
        this.gameBoardView = GameBoardViewFxml.getInstance();
        this.userFeedbackView = UserFeedbackViewFxml.getInstance(bundle);
        this.gameEventHandler = GameController.getInstance();
        this.playerEventHandler = GameController.getInstance();
        ((MenuBarViewFxml) menuBarView).setResourceBundle(this.bundle);
        this.menuBarView.initialize(this.gameEventHandler, this.gameModel);
        this.gameBoardView.initialize(this.playerEventHandler, this.boardModel);
        this.userFeedbackView.initialize(this.gameModel);
        GameController.getInstance().initialize(List.of(this.menuBarView, this.gameBoardView, this.userFeedbackView));
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("quit"));
            alert.setHeaderText(bundle.getString("sure_quit"));
            String content = bundle.getString("unsaved_are_lost");
            alert.setContentText(content);
            ButtonType okButton = new ButtonType("Ok");
            ButtonType cancelButton = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.setOnShown(e -> {
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.setAlwaysOnTop(true);
            });
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                primaryStage.close();
            } else {
                windowEvent.consume();
            }
        });
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/icon.png"))));
        BorderPane mainBorderPane = new BorderPane();
        if (!((os.contains("mac")) || (os.contains("darwin")))) {
            mainBorderPane.setTop(this.menuBarView.getNode());
        }
        mainBorderPane.setCenter(this.gameBoardView.getNode());
        mainBorderPane.setBottom(this.userFeedbackView.getNode());
        Scene scene = new Scene(mainBorderPane);
        scene.getStylesheets().add("/css/stylesheet.css");
        primaryStage.setTitle(MainFx.APP_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            Platform.runLater(() -> {
                MenuToolkit tk = MenuToolkit.toolkit();
                MenuBar bar = (MenuBar) menuBarView.getNode();
                tk.setMenuBar(primaryStage, bar);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
