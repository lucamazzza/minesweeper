package ch.supsi.minesweeper;

import ch.supsi.minesweeper.controller.GameController;
import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameEventHandler;
import ch.supsi.minesweeper.model.GameModel;
import ch.supsi.minesweeper.model.PlayerEventHandler;
import ch.supsi.minesweeper.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class MainFx extends Application {

    public static final String APP_TITLE = "mine sweeper";

    private final AbstractModel gameModel;
    private final ControlledFxView menuBarView;
    private final ControlledFxView gameBoardView;
    private final MainMenuViewFxml mainMenuView;
    private final UncontrolledFxView userFeedbackView;
    private final GameEventHandler gameEventHandler;
    private final PlayerEventHandler playerEventHandler;

    public MainFx() {
        // GAME MODEL
        this.gameModel = GameModel.getInstance();

        // VIEWS
        this.mainMenuView = MainMenuViewFxml.getInstance();
        this.menuBarView = MenuBarViewFxml.getInstance();
        this.gameBoardView = GameBoardViewFxml.getInstance();
        this.userFeedbackView = UserFeedbackViewFxml.getInstance();

        // CONTROLLERS
        this.gameEventHandler = GameController.getInstance();
        this.playerEventHandler = GameController.getInstance();

        // SCAFFOLDING of M-V-C
        this.menuBarView.initialize(this.gameEventHandler, this.gameModel);
        this.gameBoardView.initialize(this.playerEventHandler, this.gameModel);
        this.userFeedbackView.initialize(this.gameModel);
        this.mainMenuView.initialize(this.playerEventHandler, this.gameModel); // ← AGGIUNTO QUI!
        GameController.getInstance().initialize(List.of(this.menuBarView, this.gameBoardView, this.userFeedbackView));
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            primaryStage.close();
        });

        // SCAFFOLDING OF MAIN PANE
        BorderPane mainBorderPane = new BorderPane();

        mainMenuView.setRootLayout(mainBorderPane);
        mainMenuView.setGameboardView(gameBoardView);

        mainBorderPane.setCenter(mainMenuView.getNode());
        mainBorderPane.setTop(this.menuBarView.getNode());
        mainBorderPane.setBottom(this.userFeedbackView.getNode());

        // SCENE
        Scene scene = new Scene(mainBorderPane, 800, 600);

        // PRIMARY STAGE
        primaryStage.setTitle(MainFx.APP_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
