package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameModel;
import ch.supsi.minesweeper.service.UserFeedbackListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserFeedbackViewFxml implements UncontrolledFxView, UserFeedbackListener {
    private static UserFeedbackViewFxml self;
    private GameModel gameModel;
    private ResourceBundle bundle;

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

    public UserFeedbackViewFxml(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public static UserFeedbackViewFxml getInstance(ResourceBundle bundle) {
        if (self == null) {
            self = new UserFeedbackViewFxml(bundle);
            try {
                URL fxmlUrl = UserFeedbackViewFxml.class.getResource("/userfeedbackbar.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl, bundle);
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
        gameModel.addUserFeedbackListener(this);
        bombIcon.setGraphic(new FontIcon(FontAwesomeSolid.BOMB));
        flagIcon.setGraphic(new FontIcon(FontAwesomeSolid.FLAG));
        userFeedbackBar.setText(bundle.getString("welcome_message"));
        userFeedbackBar.setStyle("-fx-fill: black;");
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
                userFeedbackBar.setText(bundle.getString("userfeedback_win"));
                userFeedbackBar.setStyle("-fx-fill: green;");
            } else {
                userFeedbackBar.setText(bundle.getString("userfeedback_lose"));
                userFeedbackBar.setStyle("-fx-fill: red;");
            }
        } else {
            userFeedbackBar.setText(bundle.getString("userfeedback_in_progress"));
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

    @Override
    public void showUserFeedback(String message, UserFeedbackType type) {
        Platform.runLater(() -> {
            userFeedbackBar.setText(bundle.getString(message));
            switch (type) {
                case SUCCESS -> userFeedbackBar.setStyle("-fx-fill: green;");
                case INFO -> userFeedbackBar.setStyle("-fx-fill: blue;");
                case ERROR -> userFeedbackBar.setStyle("-fx-fill: red;");
                default -> userFeedbackBar.setStyle("-fx-fill: black;");
            }
        });
    }
}
