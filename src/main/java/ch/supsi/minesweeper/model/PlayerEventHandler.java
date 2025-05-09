package ch.supsi.minesweeper.model;

import ch.supsi.minesweeper.controller.EventHandler;

public interface PlayerEventHandler extends EventHandler {
    void leftClick(int x, int  y);
    void rightClick(int x, int y);
}
