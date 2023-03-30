package edu.virginia.cs.wordle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessResultTest {

    private GuessResult testGuessResult;

    @BeforeEach
    public void setupTestResult() {
        testGuessResult = new GuessResult();
    }

    @Test
    @DisplayName("GuessResult - constructor verification.")
    public void testThrowsErrorOnUninitializedFields() {
        assertNull(testGuessResult.getGuess());
        assertNull(testGuessResult.getAnswer());
        assertThrows(IllegalStateException.class, () ->
                testGuessResult.getGuessResult());
    }

    @Test
    @DisplayName("GuessResult - EQUIVALENCE - Correct answer Guess Result Test - All Green")
    public void testLetterResultCorrectAnswer() {
        givenInputGuessAndAnswer("MATCH", "MATCH");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("GGGGG", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - EQUIVALENCE - Some Green, some gray - BLANK, SNACK -> ggGgG")
    public void testLetterResultGrayAndGreen() {
        givenInputGuessAndAnswer("BLANK", "SHACK");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("ggGgG", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - EQUIVALENCE - Some yellow, some gray - MOUND, SNACK -> gggYg")
    public void testLetterResultGrayAndYellow() {
        givenInputGuessAndAnswer("MOUND", "SNACK");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("gggYg", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - EQUIVALENCE - All Three Colors - BRAKE, BARKS -> GYYGg")
    public void testLetterResultAllThreeColors() {
        givenInputGuessAndAnswer("BRAKE", "BARKS");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("GYYGg", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - BOUNDARY - Multi-letter yellows - ELITE, METER -> YggYY")
    public void testLetterResultMultiLetterYellows() {
        givenInputGuessAndAnswer("ELITE", "METER");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("YggYY", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - BOUNDARY - Yellow - letter count mismatch - METER, BRAKE -> gYggY")
    public void testLetterResultYellowsMismatch() {
        givenInputGuessAndAnswer("METER", "BRAKE");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("gYggY", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - BOUNDARY - Yellow - letter count mismatch - MELEE, ELITE -> gYggY")
    public void testLetterResultComplexRepeatedLetters() {
        givenInputGuessAndAnswer("MELEE", "ELITE");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("gYYgG", getLetterResultArrayAsString(guessResult));
    }

    @Test
    @DisplayName("GuessResult - EXCEPTION - bad word lengths")
    public void testBadWordLengths() {
        assertThrows(Exception.class, () -> {
           givenInputGuessAndAnswer("WORDLE", "BIRDIE");
           LetterResult[] guessResult = testGuessResult.getGuessResult();
        });
    }

    private void givenInputGuessAndAnswer(String guess, String answer) {
        testGuessResult.setGuess(guess);
        testGuessResult.setAnswer(answer);
    }

    protected static String getLetterResultArrayAsString(LetterResult[] letterResultArray) {
        StringBuilder builder = new StringBuilder(5);
        for (LetterResult letterResult : letterResultArray) {
            char letterResultCharacter = getCharacterForLetterResult(letterResult);
            builder.append(letterResultCharacter);
        }
        return builder.toString();
    }

    private static char getCharacterForLetterResult(LetterResult letterResult) {
        return switch(letterResult) {
            case GRAY -> 'g';
            case GREEN -> 'G';
            case YELLOW -> 'Y';
        };
    }

}