package edu.virginia.cs.wordle;

import java.util.Arrays;

public class GuessResult {
    public static final int GUESS_RESULT_ARRAY_SIZE = 5;
    private final LetterResult[] guessResult =
            {LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY};
    private final boolean[] setLetterUsed = new boolean[GUESS_RESULT_ARRAY_SIZE];
    private String answer; //always uppercase
    private String guess; //always uppercase

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer.toUpperCase();
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess.toUpperCase();
    }

    public LetterResult[] getGuessResult() {
        verifyAllFieldsAreInitialized();
        getGreenLetters();
        getYellowLetters();
        return guessResult;
    }

    private void verifyAllFieldsAreInitialized() {
        if (isEitherFieldNull()) {
            throw new IllegalStateException("The guess field in GuessResult must be initialized before calling getGuessResult");
        }
        if (isEitherFieldIncorrectLength()) {
            throw new IllegalArgumentException("Error: invalid length");
        }
    }

    private void getGreenLetters() {
        for (int guessIndex = 0; guessIndex < guess.length(); guessIndex++) {
            if (guessAndAnswerMatchAtIndex(guessIndex)) {
                setLetterToGreen(guessIndex);
                setLetterToUsed(guessIndex);
            }
        }
    }

    private void getYellowLetters() {
        for (int guessIndex = 0; guessIndex < guess.length(); guessIndex++) {
            if (isLetterAlreadyGreen(guessIndex)) {
                continue;
            }
            lookForMatchingYellowLetter(guessIndex);
        }
    }

    private boolean isEitherFieldNull() {
        return guess == null || answer == null;
    }

    private boolean isEitherFieldIncorrectLength() {
        return guess.length() != GUESS_RESULT_ARRAY_SIZE || answer.length() != GUESS_RESULT_ARRAY_SIZE;
    }

    private boolean guessAndAnswerMatchAtIndex(int i) {
        return guessAndAnswerLetterMatchAtIndices(i, i);
    }

    private void setLetterToGreen(int index) {
        guessResult[index] = LetterResult.GREEN;
    }

    private void setLetterToUsed(int index) {
        setLetterUsed[index] = true;
    }

    private boolean isLetterAlreadyGreen(int i) {
        return guessResult[i] == LetterResult.GREEN;
    }

    private void lookForMatchingYellowLetter(int guessIndex) {
        for (int answerIndex = 0; answerIndex < answer.length(); answerIndex++) {
            if (isAYellowLetterMatch(guessIndex, answerIndex)) {
                handleYellowLetter(guessIndex, answerIndex);
                return;
            }
        }
    }

    private boolean isAYellowLetterMatch(int guessIndex, int answerIndex) {
        return !isAnswerLetterAlreadyUsed(answerIndex) &&
                guessAndAnswerLetterMatchAtIndices(guessIndex, answerIndex);
    }

    private void handleYellowLetter(int guessIndex, int answerIndex) {
        setYellowLetterAtIndex(guessIndex);
        setLetterToUsed(answerIndex);
    }

    private void setYellowLetterAtIndex(int guessIndex) {
        guessResult[guessIndex] = LetterResult.YELLOW;
    }

    private boolean guessAndAnswerLetterMatchAtIndices(int i, int j) {
        return guess.charAt(i) == answer.charAt(j);
    }

    private boolean isAnswerLetterAlreadyUsed(int j) {
        return setLetterUsed[j];
    }

    private LetterResult[] getCorrectAnswerArray() {
        fillGuessResultArrayWithOneColor(LetterResult.GREEN);
        return guessResult;
    }

    private void fillGuessResultArrayWithOneColor(LetterResult letterResult) {
        Arrays.fill(guessResult, letterResult);
    }

}
