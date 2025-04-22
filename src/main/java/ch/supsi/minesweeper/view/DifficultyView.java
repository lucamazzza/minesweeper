package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class DifficultyView implements ControlledFxView {

    private static DifficultyView myself;

    @FXML
    private VBox difficultyView;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    private DifficultyView() {
        try {
            URL fxmlUrl = DifficultyView.class.getResource("/difficulty.fxml");
            if (fxmlUrl != null) {
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                loader.setController(this);
                loader.load();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load difficulty view", e);
        }
    }

    public static DifficultyView getInstance() {
        if (myself == null) {
            myself = new DifficultyView();
        }
        return myself;
    }

    @Override
    public void initialize(EventHandler eventHandler, AbstractModel model) {
        easyButton.setOnAction(e -> showDifficultyAlert("Easy"));
        mediumButton.setOnAction(e -> showDifficultyAlert("Medium"));
        hardButton.setOnAction(e -> showDifficultyAlert("Hard"));
    }

    private void showDifficultyAlert(String difficulty) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Difficulty Selected");
        alert.setHeaderText(null);
        alert.setContentText("You selected: " + difficulty);
        alert.showAndWait();
    }

    @Override
    public Node getNode() {
        return difficultyView;
    }

    @Override
    public void update() {
        System.out.println("DifficultyView updated");
    }
}
