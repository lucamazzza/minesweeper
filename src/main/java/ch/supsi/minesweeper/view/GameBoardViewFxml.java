package ch.supsi.minesweeper.view;

import ch.supsi.minesweeper.controller.EventHandler;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameBoardViewFxml implements ControlledFxView {

    private static GameBoardViewFxml self;

    private PlayerEventHandler playerEventHandler;

    private GameModel gameModel;

    private Button[][] buttons;
    private TileModel[][] tiles;

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

    private void createMatrix(int rows, int cols) {
        buttons = new Button[rows][cols];
        tiles = new TileModel[rows][cols];

        containerPane = new GridPane(); // Creating a GridPane to hold buttons

        containerPane.setPadding(new Insets(30)); // Add padding around the grid (10px)
        containerPane.setHgap(1); // Horizontal gap between buttons (5px)
        containerPane.setVgap(1); // Vertical gap between buttons (5px)

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                tiles[row][col] = new TileModel();

                Button button = new Button();
                button.setMinSize(40, 40);
                int finalRow = row;
                int finalCol = col;
                button.setOnMousePressed(event -> handleButtonAction(event.getButton(), finalRow, finalCol));
                buttons[row][col] = button;
                containerPane.add(button, col, row);
            }
        }
    }

    private void handleButtonAction(MouseButton bType, int row, int col) {
        TileModel tile = tiles[row][col];
        if (bType == MouseButton.PRIMARY) {
            if (!tile.isUncovered() && !tile.isMarked()) {
                tile.uncover();
                updateButton(row, col);
            }
        } else if (bType == MouseButton.SECONDARY && !tile.isUncovered()) {
                tile.flag();
                updateButton(row, col);
            }

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
    }

    private void updateButton(int row, int col) {
        TileModel tile = tiles[row][col];
        Button button = buttons[row][col];

        if (tile.isUncovered()) {
            if (tile.isBomb()) {
                button.setText("💣");
                button.setStyle("-fx-background-color: red;"); // Example styling
            } else {
//                button.setText(tile.getSurroundingMines() == 0 ? "" : String.valueOf(tile.getSurroundingMines()));
            }
            button.setDisable(true);
        } else if (tile.isMarked()) {
            button.setText("🚩");
        } else {
            button.setText("");
        }
    }
}

