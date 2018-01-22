package by.troyan.web.command.exception;

/**
 * Contains commands exceptions. This exception throws
 * in commands when it could not be executed in a different
 * reasons.
 */

public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
