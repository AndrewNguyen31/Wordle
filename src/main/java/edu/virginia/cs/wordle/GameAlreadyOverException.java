package edu.virginia.cs.wordle;

public class GameAlreadyOverException extends IllegalStateException {
    public GameAlreadyOverException(String message) {
        super(message);
    }
}
