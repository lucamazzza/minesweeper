package ch.supsi.minesweeper.model;

import lombok.Getter;
import lombok.Setter;

public class TileModel extends AbstractModel implements TileEventHandler {
    @Setter
    @Getter
    private boolean isBomb;
    @Getter
    private boolean isMarked;
    @Getter
    private boolean isExploded;
    @Getter
    private boolean isUncovered;
    @Setter
    @Getter
    private int adjBombs;

    public TileModel() {
        isBomb = false;
        isMarked = false;
        isExploded = false;
        isUncovered = false;
    }

    public TileModel(final TileModel tile) {
        isBomb = tile.isBomb;
        isMarked = tile.isMarked;
        isExploded = tile.isExploded;
        isUncovered = tile.isUncovered;
        adjBombs = tile.adjBombs;
    }

    @Override
    public void flag() {
        isMarked = !isMarked;
    }

    @Override
    public void uncover() {
        isUncovered = true;
        if (isBomb) {
            isExploded = true;
        }
    }
}