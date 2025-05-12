package ch.supsi.minesweeper.model;

import java.util.Random;

public class BoardModel extends AbstractModel implements TileEventHandler{
    public static BoardModel self;

    private static final int ROWS = 9, COLS = 9;

    private TileModel[][] tiles;

    public static BoardModel getInstance() {
        if (self == null) {
            self = new BoardModel();
        }
        return self;
    }

    @Override
    public void flag(int row, int col) {
        if (!isValidTile(row, col)){
            return;
        }
        flagTile(row, col);
    }

    private void flagTile(int row, int col) {
        TileModel tile = tiles[row][col];
        tile.flag();
    }

    private boolean isTileFlagged(int row, int col) {
        if (!isValidTile(row, col)){
            return false;
        }
        TileModel tile = tiles[row][col];
        return tile.isMarked();
    }

    public int countFlaggedTiles() {
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (isTileFlagged(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void uncover(int row, int col) {
        if (!isValidTile(row, col)){
            return;
        }
        if (isTileUncovered(row, col) && isTileFlagged(row, col)) {
            return;
        }
        if (uncoverTile(row, col)){
            return;
        }
        if (getTileAdjacentBombs(row, col) == 0) {
            uncoverAdj(row, col);
        }
    }

    private boolean uncoverTile(int row, int col) {
        TileModel tile = tiles[row][col];
        tile.uncover();
        return tile.isExploded();
    }

    private boolean isTileUncovered(int row, int col) {
        TileModel tile = tiles[row][col];
        return tile.isUncovered();
    }

    private int getTileAdjacentBombs(int row, int col) {
        TileModel tile = tiles[row][col];
        return tile.getAdjBombs();
    }

    private boolean isTileExploded(int row, int col) {
        TileModel tile = tiles[row][col];
        return tile.isExploded();
    }

    public boolean checkBombExploded() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (isTileExploded(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void uncoverAdj(int row, int col) {
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int newRow = row + r;
                int newCol = col + c;
                if (isValidTile(newRow, newCol) && !isTileUncovered(newRow, newCol)) {
                    uncover(newRow, newCol);
                }
            }
        }
    }

    public void initializeTiles(int bombs) {
        this.tiles = new TileModel[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                this.tiles[r][c] = new TileModel();
            }
        }
        int placedBombs = 0;
        Random random = new Random();
        while (placedBombs < bombs) {
            int randRow = random.nextInt(ROWS);
            int randCol = random.nextInt(COLS);
            if (!tiles[randRow][randCol].isBomb()) {
                tiles[randRow][randCol].setBomb(true);
                placedBombs++;
            }
        }
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!tiles[r][c].isBomb()) {
                    tiles[r][c].setAdjBombs(countAdjBombs(r, c));
                } else {
                    tiles[r][c].setAdjBombs(-1);
                }
            }
        }
    }

    private int countAdjBombs(int row, int col) {
        int count = 0;
        int[] directions = {-1, 0, 1};
        for (int dr : directions) {
            for (int dc : directions) {
                if (dr == 0 && dc == 0) continue;
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < tiles.length && newCol >= 0 && newCol < tiles[0].length) {
                    if (tiles[newRow][newCol].isBomb()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isValidTile(int row, int col) {
        return row >= 0 && col >= 0 && row < ROWS && col < COLS;
    }

    public int countCoveredTiles() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!isTileUncovered(row, col)){
                    count++;
                }
            }
        }
        return count;
    }

    public TileModel getTile(int row, int col) {
        if(!isValidTile(row, col)){
            return null;
        }
        return tiles[row][col];
    }
}
