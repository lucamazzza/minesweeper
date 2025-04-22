package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class DifficultyView implements ControlledFxView {

    private static DifficultyView instance;
    private StackPane difficultyView;

    @FXML
    private Button easyButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;

    public DifficultyView() {}

    public static DifficultyView getInstance() {
        if (instance == null) {
            instance = new DifficultyView();

            try {
                URL fxmlUrl = DifficultyView.class.getResource("/difficulty.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                    StackPane root = fxmlLoader.load();
                    instance = fxmlLoader.getController();
                    instance.difficultyView = root;
                }
            } catch (IOException e) {
                throw new RuntimeException("Error loading FXML", e);
            }
        }
        return instance;
    }

    @Override
    public void initialize(EventHandler eventHandler, AbstractModel model) {
        easyButton.setOnAction(event -> {
            showAlert("Easy difficulty selected");
        });

        mediumButton.setOnAction(event -> {
            showAlert("Medium difficulty selected");
        });

        hardButton.setOnAction(event -> {
            showAlert("Hard difficulty selected");
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Difficulty Selected");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public Node getNode() {
        return difficultyView;
    }

    @Override
    public void update() {
        System.out.println(this.getClass().getSimpleName() + " updated...");
    }
}
