package ch.supsi.minesweeper.model;

public interface GameEventHandler extends EventHandler {
    void newGame();

    void save();
}
