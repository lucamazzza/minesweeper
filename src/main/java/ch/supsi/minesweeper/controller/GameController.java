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

    private GameController () {
        this.gameModel = GameModel.getInstance();
    }

    public static GameController getInstance() {
        if (self == null) {
            self = new GameController();
        }
        return self;
    }

    public void initialize(List<DataView> views) {
        initTileMatrix(9, 9, 10);
        this.views = views;
    }

    @Override
    public void newGame() {
        // do whatever you must do to start a new game

        // then update your views
        this.views.forEach(DataView::update);
    }

    @Override
    public void save() {
        // do whatever you must do to start a new game

        // then update your views
        this.views.forEach(DataView::update);
    }

    // add all the relevant methods to handle all those defined by the GameEventHandler interface
    // ...

    @Override
    public void action() {
        this.gameModel.action();
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
                if (dr == 0 && dc == 0) continue; // Skip the current tile
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
            views.forEach(DataView::update);
            if (tile.isBomb()) {
                // TODO: Handle game won
            } else {
                // TODO: Handle game won
            }
        }
    }

    public void handleRightClick(int row, int col) {
        TileModel tile = tiles[row][col];
        if (!tile.isUncovered()) {
            tile.flag();
            views.forEach(DataView::update);
        }
    }

    public void startNewGame(int rows, int cols, int bombCount) {
        initTileMatrix(rows, cols, bombCount);
        views.forEach(DataView::update);
    }


    public TileModel getTile(int row, int col) {
        return new TileModel(tiles[row][col]);
    }
}
