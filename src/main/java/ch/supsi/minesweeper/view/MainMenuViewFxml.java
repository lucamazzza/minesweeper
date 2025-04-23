package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class MainMenuViewFxml implements ControlledFxView {

    private static MainMenuViewFxml myself;
    private BorderPane rootLayout;
    private DifficultyView difficultyView;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button startNewGameButton;

    @FXML
    private StackPane mainMenuView;

    @FXML
    private Button loadGameButton;

    @FXML
    private Button rulesButton;

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    private MainMenuViewFxml() {}

    public static MainMenuViewFxml getInstance() {
        if (myself == null) {
            myself = new MainMenuViewFxml();

            try {
                URL fxmlUrl = MainMenuViewFxml.class.getResource("/mainmenu.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                    fxmlLoader.setController(myself);
                    fxmlLoader.load();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return myself;
    }

    @Override
    public void initialize(EventHandler eventHandler, AbstractModel model) {
        // Inizializza la DifficultyView
        difficultyView = DifficultyView.getInstance();

        startNewGameButton.setOnAction(event -> {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("New Game");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("You are about to start a new game, do you want to continue?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    rootLayout.setCenter(difficultyView.getNode());
                }
            });
        });

        loadGameButton.setOnAction(event -> {
            Alert loadAlert = new Alert(Alert.AlertType.INFORMATION);
            loadAlert.setTitle("Load Game");
            loadAlert.setHeaderText(null);
            loadAlert.setContentText("Load game functionality not implemented yet.");
            loadAlert.showAndWait();
        });

        rulesButton.setOnAction(event -> {
            Alert rulesAlert = new Alert(Alert.AlertType.INFORMATION);
            rulesAlert.setTitle("Game Rules");
            rulesAlert.setHeaderText("Minesweeper Rules");
            rulesAlert.setContentText("""
            - Uncover all safe tiles without triggering a mine.
            - Numbers indicate how many mines are adjacent.
            - Right-click to flag suspected mines.
            - Good luck!
        """);
            rulesAlert.showAndWait();
        });
    }

    @Override
    public Node getNode() {
        return mainMenuView;
    }

    @Override
    public void update() {
        System.out.println(this.getClass().getSimpleName() + " updated..." + System.currentTimeMillis());
    }
}
