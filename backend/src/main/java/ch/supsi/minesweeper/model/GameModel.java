package ch.supsi.minesweeper.model;

import ch.supsi.minesweeper.data.TileModelCsvExporter;
import ch.supsi.minesweeper.data.TileModelCsvImporter;
import ch.supsi.minesweeper.service.UserFeedbackListener;
import ch.supsi.minesweeper.service.UserFeedbackListener.UserFeedbackType;
import ch.supsi.minesweeper.service.UserPreferences;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GameModel extends AbstractModel implements GameEventHandler, TileEventHandler {
    private static GameModel self;
    private final BoardModel boardModel;
    private int flagsPlaced;
    @Setter private int bombsAmount;
    private boolean gameOverState;
    private boolean gameWon;
    @Setter private File currentSave;
    private final List<UserFeedbackListener> feedbackListeners = new ArrayList<>();


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
        currentSave = null;
        flagsPlaced = 0;
        bombsAmount = UserPreferences.getInstance().getBombs();
        gameWon = false;
        gameOverState = false;
        boardModel.initializeTiles(bombsAmount);
    }

    @Override
    public void save() {
        if (currentSave == null) {
            notifyUserFeedback("userfeedback_no_savefile", UserFeedbackType.ERROR);
            return;
        }
        TileModel[][] tiles = boardModel.getTiles();
        try {
            TileModelCsvExporter.exportToFile(tiles, currentSave);
            notifyUserFeedback("userfeedback_successful_save", UserFeedbackType.SUCCESS);  // verde
        } catch (IOException e) {
            notifyUserFeedback("userfeedback_unsuccessful_save", UserFeedbackType.ERROR);  // rosso
        }
    }

    @Override
    public void saveAs(File file) {
        if (file != null) {
            currentSave = file;
            save();
        } else {
            notifyUserFeedback("userfeedback_no_savefile", UserFeedbackType.ERROR);
        }
    }

    @Override
    public void open(File file) {
        if (file != null) {
            try {
                boardModel.setTiles(TileModelCsvImporter.importTilesFromSave(file));
                currentSave = file;
                bombsAmount = countBombs();
                gameWon = false;
                gameOverState = false;
                updateFlagCount();
                updateVictoryStatus();
                notifyUserFeedback("userfeedback_successful_load", UserFeedbackType.INFO);  // blu
            } catch (IOException e) {
                notifyUserFeedback("userfeedback_unsuccessful_load", UserFeedbackType.ERROR);  // rosso
            }
        }
    }

    @Override
    public void quit() {
        Platform.exit();
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

    private int countBombs() {
        int totalBombs = 0;
        for (int x = 0; x < Constant.GRID_HEIGHT; x++) {
            for (int y = 0; y < Constant.GRID_WIDTH; y++) {
                if (boardModel.getTile(x, y).isBomb()) {
                    totalBombs++;
                }
            }
        }
        return totalBombs;
    }

    public void addUserFeedbackListener(UserFeedbackListener listener) {
        feedbackListeners.add(listener);
    }

    public void removeUserFeedbackListener(UserFeedbackListener listener) {
        feedbackListeners.remove(listener);
    }

    private void notifyUserFeedback(String message, UserFeedbackType type) {
        for (UserFeedbackListener listener : feedbackListeners) {
            listener.showUserFeedback(message, type);
        }
    }
}
