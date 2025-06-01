package ch.supsi.minesweeper.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constant {
    // GRID SIZING /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final int GRID_HEIGHT = 9;
    public static final int GRID_WIDTH  = 9;
    public static final int TILE_COUNT  = GRID_HEIGHT * GRID_WIDTH;
    // BOMB INVARIANTS /////////////////////////////////////////////////////////////////////////////////////////////////
    public static final int MIN_BOMBS       = 1;
    public static final int MAX_BOMBS       = TILE_COUNT - 1;
    public static final int DEFAULT_BOMBS   = 10;
    public static final String DEFAULT_LANGUAGE = "en";
    // CONFIG
    public static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".minesweeper", "config.toml");

    private Constant() {}
}
