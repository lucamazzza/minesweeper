package ch.supsi.minesweeper.controller;

import ch.supsi.minesweeper.model.Constant;
import ch.supsi.minesweeper.model.GameEventHandler;
import ch.supsi.minesweeper.model.GameModel;
import ch.supsi.minesweeper.model.PlayerEventHandler;
import ch.supsi.minesweeper.view.DataView;

import java.io.File;
import java.util.List;

public class GameController implements GameEventHandler, PlayerEventHandler {
    private static GameController self;
    private GameModel gameModel;
    private List<DataView> views;

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
        gameModel.newGame();
        this.views.forEach(DataView::enable);
        this.views.forEach(DataView::update);
    }

    @Override
    public void save() {
        gameModel.save();
        this.views.forEach(DataView::update);
    }

    @Override
    public void saveAs(File file) {
        gameModel.saveAs(file);
        this.views.forEach(DataView::update);
    }

    @Override
    public void open(File file) {
        gameModel.open(file);
        this.views.forEach(DataView::enable);
        this.views.forEach(DataView::update);
    }

    @Override
    public void quit() {
        gameModel.quit();
    }

    @Override
    public void leftClick(int row, int col) {
        gameModel.uncover(row, col);
        checkGameOver();
        views.forEach(DataView::update);
    }

    @Override
    public void rightClick(int row, int col) {
        gameModel.flag(row, col);
        views.forEach(DataView::update);
    }

    private void checkGameOver() {
        if (!gameModel.isGameOverState()) {
            return;
        }
        boolean won = gameModel.isGameWon();
        if (!won) {
            for (int r = 0; r < Constant.GRID_HEIGHT; r++) {
                for (int c = 0; c < Constant.GRID_WIDTH; c++) {
                    gameModel.uncover(r, c);
                }
            }
        }
        views.forEach(DataView::disable);
    }
}
