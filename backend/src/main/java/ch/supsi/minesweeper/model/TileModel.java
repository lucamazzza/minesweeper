package ch.supsi.minesweeper.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TileModel extends AbstractModel {
    @Setter
    private boolean isBomb;
    private boolean isMarked;
    private boolean isExploded;
    private boolean isUncovered;
    @Setter
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

    public void flag() {
        if (isUncovered) {
            return;
        }
        isMarked = !isMarked;
    }

    public void uncover() {
        if (isMarked || isUncovered) {
            return;
        }
        isUncovered = true;
        if (isBomb) {
            isExploded = true;
        }
    }
}