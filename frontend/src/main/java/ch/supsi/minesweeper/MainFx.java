package ch.supsi.minesweeper;

import ch.supsi.minesweeper.controller.GameController;
import ch.supsi.minesweeper.model.*;
import ch.supsi.minesweeper.service.UserPreferences;
import ch.supsi.minesweeper.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainFx extends Application {
    public static final String APP_TITLE = "mine sweeper";
    private final AbstractModel gameModel;
    private final ControlledFxView menuBarView;
    private final ControlledFxView gameBoardView;
    private final UncontrolledFxView userFeedbackView;
    private final EventHandler gameEventHandler;
    private final PlayerEventHandler playerEventHandler;
    private final ResourceBundle bundle;


    public MainFx() {
        UserPreferences preferences = UserPreferences.getInstance();
        this.bundle = preferences.getMessages();
        // GAME MODEL
        this.gameModel = GameModel.getInstance();
        // VIEWS
        this.menuBarView = MenuBarViewFxml.getInstance(bundle);
        this.gameBoardView = GameBoardViewFxml.getInstance();
        this.userFeedbackView = UserFeedbackViewFxml.getInstance(bundle);
        // CONTROLLERS
        this.gameEventHandler = GameController.getInstance();
        this.playerEventHandler = GameController.getInstance();
        // SCAFFOLDING of M-V-C
        ((MenuBarViewFxml) menuBarView).setResourceBundle(this.bundle);
        menuBarView.initialize(this.gameEventHandler, this.gameModel);
        this.gameBoardView.initialize(this.playerEventHandler, this.gameModel);
        this.userFeedbackView.initialize(this.gameModel);
        GameController.getInstance().initialize(List.of(this.menuBarView, this.gameBoardView, this.userFeedbackView));
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            // quit the app
            // replace this hard close
            primaryStage.close();
        });
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/icon.png"))));
        // SCAFFOLDING OF MAIN PANE
        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setCenter(gameBoardView.getNode());
        mainBorderPane.setTop(this.menuBarView.getNode());
        // FIXME: Only on windows this must be performed????
        mainBorderPane.setTop(this.menuBarView.getNode());
        mainBorderPane.setCenter(this.gameBoardView.getNode());
        mainBorderPane.setBottom(this.userFeedbackView.getNode());
        // SCENE
        Scene scene = new Scene(mainBorderPane);
        scene.getStylesheets().add("/css/stylesheet.css");
        // PRIMARY STAGE
        primaryStage.setTitle(MainFx.APP_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();
//        MenuToolkit tk = MenuToolkit.toolkit();
//        MenuBar bar = (MenuBar) menuBarView.getNode();
//        tk.setMenuBar(primaryStage, bar);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
