package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.model.AbstractModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class DifficultyView implements ControlledFxView {

    private static DifficultyView myself;

    private BorderPane rootLayout;

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }


    @FXML
    private VBox difficultyView;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private Button hellButton;

    @FXML
    private Button surpassButton;

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
        hellButton.setOnAction(e -> showDifficultyAlert("Hell"));
        surpassButton.setOnAction(e -> showDifficultyAlert("Surpass Your Limits"));
    }

    private void showDifficultyAlert(String difficulty) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma nuova partita");
        alert.setHeaderText(null);
        alert.setContentText("Stai per cominciare una nuova partita in " + difficulty.toLowerCase() + " mode. Vuoi continuare?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Nuova partita avviata in modalità: " + difficulty);
                rootLayout.setCenter(GameBoardViewFxml.getInstance().getNode());
            } else {
                System.out.println("Partita annullata");
            }
        });
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
