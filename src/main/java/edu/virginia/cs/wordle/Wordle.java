package edu.virginia.cs.wordle;

public interface Wordle {
    default Wordle getGame() {
        return new WordleImplementation();
    }

    boolean isGameOver();
    boolean isWin();
    boolean isLoss();
    int getGuessCount();
    int getRemainingGuesses();
    String getAnswer();
    LetterResult[] submitGuess(String guess);
}
