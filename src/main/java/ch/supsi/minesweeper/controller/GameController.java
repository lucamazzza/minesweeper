package ch.supsi.minesweeper.controller;

import ch.supsi.minesweeper.model.GameEventHandler;
import ch.supsi.minesweeper.model.GameModel;
import ch.supsi.minesweeper.model.PlayerEventHandler;
import ch.supsi.minesweeper.model.TileModel;
import ch.supsi.minesweeper.view.DataView;

import java.util.List;
import java.util.Random;

public class GameController implements GameEventHandler, PlayerEventHandler {
    private static GameController self;
    private GameModel gameModel;
    private List<DataView> views;
    private TileModel[][] tiles;
    private boolean gameOverState = false;

    private GameController() {
        this.gameModel = GameModel.getInstance();
    }

    public static GameController getInstance() {
        if (self == null) {
            self = new GameController();
        }
        return self;
    }

    public void initialize(List<DataView> views) {
        this.views = views;
    }

    @Override
    public void newGame() {
        initTileMatrix(9, 9, 10);
        this.views.forEach(DataView::enable);
        this.views.forEach(DataView::update);
    }

    @Override
    public void save() {
        this.views.forEach(DataView::update);
    }

    @Override
    public void action() {
        views.forEach(DataView::update);
    }

    private void initTileMatrix(int rows, int cols, int bombCount) {
        this.tiles = new TileModel[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                this.tiles[r][c] = new TileModel();
            }
        }
        int placedBombs = 0;
        Random random = new Random();
        while (placedBombs < bombCount) {
            int randRow = random.nextInt(rows);
            int randCol = random.nextInt(cols);
            if (!tiles[randRow][randCol].isBomb()) {
                tiles[randRow][randCol].setBomb(true);
                placedBombs++;
            }
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!tiles[r][c].isBomb()) {
                    tiles[r][c].setAdjBombs(countAdjBombs(r, c));
                } else {
                    tiles[r][c].setAdjBombs(-1);
                }
            }
        }
    }

    private int countAdjBombs(int row, int col) {
        int count = 0;
        int[] directions = {-1, 0, 1};
        for (int dr : directions) {
            for (int dc : directions) {
                if (dr == 0 && dc == 0) continue;
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < tiles.length && newCol >= 0 && newCol < tiles[0].length) {
                    if (tiles[newRow][newCol].isBomb()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void handleLeftClick(int row, int col) {
        TileModel tile = tiles[row][col];
        if (!tile.isUncovered() && !tile.isMarked()) {
            tile.uncover();
            if (tile.getAdjBombs() == 0) {
                uncoverAdj(row, col);
            }
            views.forEach(DataView::update);
            if (tile.isBomb()) {
                gameOver(false);
            } else if (checkVictory()) {
                gameOver(true);
            }
        }
    }

    private void uncoverAdj(int row, int col) {
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int newRow = row + r;
                int newCol = col + c;
                if (isValidTile(newRow, newCol) && !tiles[newRow][newCol].isUncovered()) {
                    handleLeftClick(newRow, newCol);
                }
            }
        }
    }

    private boolean checkVictory() {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                if (!tiles[r][c].isBomb() && !tiles[r][c].isUncovered()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void gameOver(boolean won) {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                tiles[r][c].uncover();
            }
        }
        views.forEach(DataView::disable);
        views.forEach(DataView::update);  // Aggiungi questa linea per aggiornare la vista
        showAlert(won ? "Victory!" : "Game Over", won ? "Congratulations, you won!" : "Oops, you clicked on a bomb!");
    }

    private void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void handleRightClick(int row, int col) {
        TileModel tile = tiles[row][col];
        if (!tile.isUncovered()) {
            tile.flag();
            views.forEach(DataView::update);
        }
    }

    public TileModel getTile(int row, int col) {
        return new TileModel(tiles[row][col]);
    }

    private boolean isValidTile(int row, int col) {
        return row >= 0 && col >= 0 && row < tiles.length && col < tiles[0].length;
    }
}
