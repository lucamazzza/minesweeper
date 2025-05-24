package ch.supsi.minesweeper.model;

import ch.supsi.minesweeper.service.UserPreferences;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameModel extends AbstractModel implements GameEventHandler, TileEventHandler {
    private static GameModel self;
    // MODELS
    private final BoardModel boardModel;

    private int flagsPlaced;
    @Setter
    private int bombsAmount;
    private boolean gameOverState;
    private boolean gameWon;

    private GameModel() {
        super();
        boardModel = BoardModel.getInstance();
    }

    public static GameModel getInstance() {
        if (self == null) {
            self = new GameModel();
        }
        return self;
    }

    @Override
    public void newGame() {
        flagsPlaced = 0;
        bombsAmount = UserPreferences.getInstance().getBombs();
        gameWon = false;
        gameOverState = false;
        boardModel.initializeTiles(bombsAmount);
    }

    @Override
    public void save() {

    }

    @Override
    public void flag(int row, int col) {
        boardModel.flag(row, col);
        updateFlagCount();
    }

    @Override
    public void uncover(int row, int col) {
        boardModel.uncover(row, col);
        updateVictoryStatus();
    }

    private void updateFlagCount() {
        flagsPlaced = boardModel.countFlaggedTiles();
    }

    private void updateVictoryStatus() {
        gameOverState = boardModel.checkBombExploded();
        if (gameOverState) {
            gameWon = false;
            return;
        }
        if (bombsAmount == boardModel.countCoveredTiles()) {
            gameOverState = true;
            gameWon = true;
        }
    }
}
