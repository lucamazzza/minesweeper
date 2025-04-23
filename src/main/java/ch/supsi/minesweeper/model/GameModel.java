package ch.supsi.minesweeper.model;

public class GameModel extends AbstractModel implements GameEventHandler, PlayerEventHandler {

    private static GameModel self;

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

    }

    @Override
    public void save() {

    }

    @Override
    public void action() {
        return;
    }

    // add all the relevant missing behaviours
    // ...

}
