// InvalidBombsException.java
package ch.supsi.minesweeper.exception;

public class InvalidBombsException extends Exception {
    private final String messageKey;

    public InvalidBombsException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
