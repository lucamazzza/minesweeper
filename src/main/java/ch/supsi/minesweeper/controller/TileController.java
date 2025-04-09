package ch.supsi.minesweeper.controller;

import ch.supsi.minesweeper.model.TileEventHandler;
import ch.supsi.minesweeper.model.TileModel;
import ch.supsi.minesweeper.view.DataView;
import ch.supsi.minesweeper.view.GameBoardViewFxml;

public class TileController implements TileEventHandler {

    private final TileModel model;
    private DataView gameBoardViewFxml;

    public TileController () {
        model = new TileModel();
        gameBoardViewFxml = GameBoardViewFxml.getInstance();
    }

    @Override
    public void flag() {
        model.flag();
        gameBoardViewFxml.update();
    }

    @Override
    public void uncover() {
        model.uncover();
        gameBoardViewFxml.update();
    }


}
