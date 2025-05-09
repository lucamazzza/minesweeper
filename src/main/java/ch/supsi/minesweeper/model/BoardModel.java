package ch.supsi.minesweeper.model;

public class BoardModel extends AbstractModel implements TileEventHandler{
    public static BoardModel self;

    public static BoardModel getInstance() {
        if (self == null) {
            self = new BoardModel();
        }
        return self;
    }

    @Override
    public void flag() {

    }

    @Override
    public void uncover() {

    }
}
