package ch.supsi.minesweeper.model;

import ch.supsi.minesweeper.controller.EventHandler;

public interface TileEventHandler extends EventHandler {
    void flag(int row, int col);
    void uncover(int row, int col);
}
