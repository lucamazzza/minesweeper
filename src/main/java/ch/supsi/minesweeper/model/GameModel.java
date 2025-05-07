package ch.supsi.minesweeper.model;

import ch.supsi.minesweeper.service.UserPreferences;
import lombok.Getter;
import lombok.Setter;

public class GameModel extends AbstractModel implements GameEventHandler {
    private static GameModel self;
    @Getter
    private int flagsPlaced;
    @Getter
    @Setter
    private int bombsAmount;

    private GameModel() {
        super();
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
        bombsAmount = new UserPreferences().getBombs();
    }

    @Override
    public void save() {

    }

    public void incrementFlagsPlaced() {
        this.flagsPlaced++;
    }

    public void decrementFlagsPlaced() {
        if (flagsPlaced > 0) {
            this.flagsPlaced--;
        }
    }
}
