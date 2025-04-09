package ch.supsi.minesweeper.model;

public class TileModel extends AbstractModel implements TileEventHandler{
    private boolean hasBomb;
    private boolean isMarked;

    public TileModel() {
        hasBomb = false;
        isMarked = false;
    }

    @Override
    public void flag() {
        if (hasBomb) {
            // Qualcosa
            return;
        }
        if (isMarked) {
            // Qualcosa
            return;
        }
        isMarked = true;
    }

    @Override
    public void uncover() {
        if (hasBomb) {
            // Qualcosa
            return;
        }
        //Qualcosa
    }
}