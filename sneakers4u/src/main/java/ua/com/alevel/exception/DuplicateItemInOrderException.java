package ua.com.alevel.exception;

public class DuplicateItemInOrderException extends RuntimeException {

    public DuplicateItemInOrderException(String message) {
        super(message);
    }
}
