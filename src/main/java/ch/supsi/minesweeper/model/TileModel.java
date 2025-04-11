package ch.supsi.minesweeper.model;

import lombok.Getter;

public class TileModel extends AbstractModel implements TileEventHandler{
    private final boolean isBomb;
    @Getter
    private boolean isMarked;
    @Getter
    private boolean isExploded;

    public TileModel(final boolean isBomb) {
        this.isBomb = isBomb;
        isMarked = false;
        isExploded = false;
    }

    @Override
    public void flag() {
        if (isMarked) {
            isMarked = false;
            return;
        }
        isMarked = true;
    }

    @Override
    public void uncover() {
        if (isBomb) {
            isExploded = true;
        }
    }
}