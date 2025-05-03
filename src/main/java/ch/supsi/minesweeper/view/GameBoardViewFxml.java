package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
import ch.supsi.minesweeper.controller.GameController;
import ch.supsi.minesweeper.model.AbstractModel;
import ch.supsi.minesweeper.model.GameModel;
import ch.supsi.minesweeper.model.PlayerEventHandler;
import ch.supsi.minesweeper.model.TileModel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameBoardViewFxml implements ControlledFxView {

    private static GameBoardViewFxml self;
    private PlayerEventHandler playerEventHandler;
    private GameModel gameModel;
    private Button[][] buttons;

    @FXML
    private GridPane containerPane;

    private GameBoardViewFxml() {
    }

    public static GameBoardViewFxml getInstance() {
        if (self == null) {
            self = new GameBoardViewFxml();
        }
        return self;
    }

    @Override
    public void initialize(EventHandler eventHandler, AbstractModel model) {
        this.createMatrix(9, 9);
        this.playerEventHandler = (PlayerEventHandler) eventHandler;
        this.gameModel = (GameModel) model;
    }

    @Override
    public Node getNode() {
        return this.containerPane;
    }

    @Override
    public void update() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(this.getClass().getSimpleName() + " updated..." + dateFormat.format(date));
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                updateButton(r, c);
            }
        }
    }

    @Override
    public void enable() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                setDisableButton(r, c, false);
            }
        }
    }

    @Override
    public void disable() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                setDisableButton(r, c, true);
            }
        }
    }

    private void createMatrix(int rows, int cols) {
        buttons = new Button[rows][cols];

        containerPane = new GridPane(); // Creating a GridPane to hold buttons

        containerPane.setPadding(new Insets(30)); // Add padding around the grid (10px)
        containerPane.setHgap(1); // Horizontal gap between buttons (5px)
        containerPane.setVgap(1); // Vertical gap between buttons (5px)

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                Button button = new Button();
                button.setMinSize(40, 40);
                button.setDisable(true);
                int finalRow = r;
                int finalCol = c;
                button.setOnMousePressed(event -> handleButtonAction(event.getButton(), finalRow, finalCol));
                buttons[r][c] = button;
                containerPane.add(button, c, r);
            }
        }
    }

    private void handleButtonAction(MouseButton bType, int row, int col) {
        if (bType == MouseButton.PRIMARY) {
            GameController.getInstance().handleLeftClick(row, col);
            updateButton(row, col);
        } else if (bType == MouseButton.SECONDARY) {
            GameController.getInstance().handleRightClick(row, col);
            updateButton(row, col);
        }
    }

    private void updateButton(int row, int col) {
        Button button = buttons[row][col];
        TileModel tile = GameController.getInstance().getTile(row, col);
        if (tile.isUncovered()) {
            if (tile.isBomb()) {
                FontIcon icon = new FontIcon(FontAwesome.BOMB);
                icon.setIconSize(14);
                icon.setIconColor(Paint.valueOf("white"));
                button.setGraphic(icon);
                button.setStyle("-fx-background-color: red;");
            } else {
                button.setText(tile.getAdjBombs() == 0 ? "" : String.valueOf(tile.getAdjBombs()));
            }
            button.setDisable(true);
        } else if (tile.isMarked()) {
            FontIcon icon = new FontIcon(FontAwesome.FLAG);
            icon.setIconSize(14);
            icon.setIconColor(Paint.valueOf("white"));
            button.setGraphic(icon);
        } else {
            button.setGraphic(null);
            button.setStyle("-fx-background-color: #3b3b3b;");
            button.setText("");
        }
    }

    public void disableAllButtons() {
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[0].length; c++) {
                buttons[r][c].setDisable(true);
            }
        }
    }

    private void setDisableButton(int row, int col, boolean disable) {
        buttons[row][col].setDisable(disable);
    }
}

