package it.polimi.db69.telco.telcoejb.exceptions;

public class NonUniqueException extends Exception {
    private static final long serialVersionUID = 1L;

    public NonUniqueException(String message) {
        super(message);
    }
}
