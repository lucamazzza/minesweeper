package ch.supsi.minesweeper.model;

import lombok.Getter;
import lombok.Setter;

public class TileModel extends AbstractModel implements TileEventHandler{
    @Setter
    @Getter
    private boolean isBomb;
    @Getter
    private boolean isMarked;
    @Getter
    private boolean isExploded;

    public TileModel() {
        isBomb = false;
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