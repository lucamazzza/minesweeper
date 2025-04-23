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
        int minBombs = 0;
        int maxBombs = 0;
        String extraMessage = "";

        switch (difficulty) {
            case "Easy":
                minBombs = 1;
                maxBombs = 15;
                break;
            case "Medium":
                minBombs = 16;
                maxBombs = 30;
                break;
            case "Hard":
                minBombs = 31;
                maxBombs = 45;
                break;
            case "Hell":
                minBombs = 55;
                maxBombs = 60;
                break;
            case "Surpass Your Limits":
                minBombs = 40;
                maxBombs = 50;
                extraMessage = "\nfurthermore you'll have to beat a timer!";
                break;
        }

        int bombCount = minBombs + (int) (Math.random() * (maxBombs - minBombs + 1));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm a new game");
        alert.setHeaderText(null);
        alert.setContentText("You're about to start a new game in " + difficulty.toLowerCase() + " mode."
                + "\nNumber of bombs: " + bombCount + extraMessage + "\nDo you want to continue?");

        int finalBombCount = bombCount;
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("New game started in: " + difficulty + " mode with " + finalBombCount + " bombs.");
                rootLayout.setCenter(GameBoardViewFxml.getInstance().getNode());
            } else {
                System.out.println("Game canceled");
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
