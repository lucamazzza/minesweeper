package ch.supsi.minesweeper.service;

public interface UserFeedbackListener {
    void showUserFeedback(String message, UserFeedbackType type);

    enum UserFeedbackType {
        INFO,
        SUCCESS,
        ERROR
    }
}
