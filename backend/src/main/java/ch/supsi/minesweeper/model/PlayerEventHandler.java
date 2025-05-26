package ch.supsi.minesweeper.model;

public interface PlayerEventHandler extends EventHandler {
    void leftClick(int x, int  y);
    void rightClick(int x, int y);
}
