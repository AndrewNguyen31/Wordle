package edu.virginia.cs.wordle;

public class IllegalWordException extends IllegalArgumentException {
    public IllegalWordException(String message) {
        super(message);
    }
}
