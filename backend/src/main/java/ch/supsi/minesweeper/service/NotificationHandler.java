package ch.supsi.minesweeper.service;

import java.util.ResourceBundle;

public interface NotificationHandler {
    void notifyInfo(String message);
    void notifyWarning(String message);
    void notifyError(String message);
    void setBundle(ResourceBundle bundle);
}
