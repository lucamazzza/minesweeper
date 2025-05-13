package ch.supsi.minesweeper.model;

public final class Constant {
    // GRID SIZING /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final int GRID_HEIGHT = 9;
    public static final int GRID_WIDTH  = 9;
    public static final int TILE_COUNT  = GRID_HEIGHT * GRID_WIDTH;
    // BOMB INVARIANTS /////////////////////////////////////////////////////////////////////////////////////////////////
    public static final int MIN_BOMBS       = 1;
    public static final int MAX_BOMBS       = TILE_COUNT - 1;
    public static final int DEFAULT_BOMBS  = 10;

    private Constant() {}
}
