package ch.supsi.minesweeper.view;
import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class MainMenuViewFxml implements ControlledFxView {

    private static MainMenuViewFxml myself;
    private BorderPane rootLayout;
    private ControlledFxView gameboardView;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button startNewGameButton;

    @FXML
    private AnchorPane mainMenuView;

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void setGameboardView(ControlledFxView gameboardView) {
        this.gameboardView = gameboardView;
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

        startNewGameButton.setOnAction(event -> {
            rootLayout.setCenter(gameboardView.getNode());
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
