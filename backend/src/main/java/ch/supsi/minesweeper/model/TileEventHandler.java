package ch.supsi.minesweeper.model;

public interface TileEventHandler extends EventHandler {
    void flag(int row, int col);
    void uncover(int row, int col);
}
