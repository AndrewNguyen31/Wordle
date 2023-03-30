package edu.virginia.cs.wordle;

public class EmptyDictionaryException extends IllegalStateException {
    public EmptyDictionaryException(String message) {
        super(message);
    }
}
