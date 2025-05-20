package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserFeedbackViewFxml implements UncontrolledFxView {
    private static UserFeedbackViewFxml self;
    private GameModel gameModel;
    @FXML
    private ScrollPane containerPane;
    @FXML
    private Label bombIcon;
    @FXML
    private Label flagIcon;
    @FXML
    private Text bombCount;
    @FXML
    private Text flagCount;

    @FXML
    private Text userFeedbackBar;
    private UserFeedbackViewFxml() {}

    public static UserFeedbackViewFxml getInstance() {
        if (self == null) {
            self = new UserFeedbackViewFxml();
            try {
                URL fxmlUrl = UserFeedbackViewFxml.class.getResource("/userfeedbackbar.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                    fxmlLoader.setController(self);
                    fxmlLoader.load();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return self;
    }

    @Override
    public void initialize(AbstractModel model) {
        this.gameModel = (GameModel) model;
        bombIcon.setGraphic(new FontIcon(FontAwesome.BOMB));
        flagIcon.setGraphic(new FontIcon(FontAwesome.FLAG));
    }

    @Override
    public Node getNode() {
        return this.containerPane;
    }

    @Override
    public void update() {
        bombCount.setText(gameModel.getBombsAmount() + "");
        flagCount.setText(gameModel.getBombsAmount() - gameModel.getFlagsPlaced() + "");
        if (gameModel.isGameOverState()) {
            if (gameModel.isGameWon()) {
                userFeedbackBar.setText("You Won!");
                userFeedbackBar.setStyle("-fx-fill: green;");
            } else {
                userFeedbackBar.setText("Game Over!");
                userFeedbackBar.setStyle("-fx-fill: red;");
            }
        } else {
            userFeedbackBar.setText("Game in progress...");
            userFeedbackBar.setStyle("-fx-fill: black;");
        }
    }

    @Override
    public void enable() {
        System.out.println("UserFeedbackView Enabled");
    }

    @Override
    public void disable() {
        System.out.println("UserFeedbackView Disabled");
    }
}
