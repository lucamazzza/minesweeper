package ch.supsi.minesweeper.model;

public interface PlayerEventHandler extends EventHandler {
    void leftClick(int row, int col);
    void rightClick(int row, int col);
}
