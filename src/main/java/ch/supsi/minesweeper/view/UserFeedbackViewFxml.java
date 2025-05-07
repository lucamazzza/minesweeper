package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

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
    }

    @Override
    public Node getNode() {
        return this.containerPane;
    }

    @Override
    public void update() {
        int flagCount = gameModel.getFlagsPlaced();
        String userFeedbackText = new StringBuilder("🚩")
                .append(flagCount)
                .append(" | 💣 ")
                .append(gameModel.getBombsAmount())
                .toString();
        this.userFeedbackBar.setText(userFeedbackText);
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
