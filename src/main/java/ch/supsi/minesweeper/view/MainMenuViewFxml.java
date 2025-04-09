package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

public class MainMenuViewFxml implements ControlledFxView {

    private static MainMenuViewFxml myself;

    @FXML
    private Label welcomeLabel;

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
        // Se non è necessario, lascia il metodo vuoto
    }

    @Override
    public Node getNode() {
        return welcomeLabel;
    }

    @Override
    public void update() {
        System.out.println(this.getClass().getSimpleName() + " updated..." + System.currentTimeMillis());
    }
}
