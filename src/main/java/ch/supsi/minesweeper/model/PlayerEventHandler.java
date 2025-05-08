package ch.supsi.minesweeper.model;

import ch.supsi.minesweeper.controller.EventHandler;

import java.awt.event.MouseEvent;

public interface PlayerEventHandler extends EventHandler {
    void action(MouseEvent mouseEvent, int x, int  y);
}
