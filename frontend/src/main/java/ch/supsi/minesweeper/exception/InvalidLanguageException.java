// InvalidLanguageException.java
package ch.supsi.minesweeper.exception;

public class InvalidLanguageException extends Exception {
    private final String messageKey;

    public InvalidLanguageException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
