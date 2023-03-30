package edu.virginia.cs.wordle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private static WordleDictionary defaultGuessDictionary;
    private WordleImplementation testGame;

    @BeforeAll
    public static void initializeGuessDictionary() {
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        defaultGuessDictionary = factory.getDefaultGuessesDictionary();
    }

    @BeforeEach
    public void setupDefaultTestState() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 0, WordleImplementation.GameStatus.PLAYING);
    }

    @Test
    public void testConstructorWithIllegalAnswer() {
        assertThrows(IllegalWordException.class,
                () -> new WordleImplementation("QZXYX"));
    }

    @Test
    public void testZeroArgConstructor() {
        WordleImplementation noArg = new WordleImplementation();
        assertEquals(0, noArg.getGuessCount());
        assertFalse(noArg.isGameOver());
    }

    @Test
    public void testZeroOneConstructor_ValidAnswer() {
        WordleImplementation noArg = new WordleImplementation("MATCH");
        assertEquals(0, noArg.getGuessCount());
        assertFalse(noArg.isGameOver());
    }

    @Test
    @DisplayName("GameState - EQUIVALENCE - first guess that isn't the answer")
    public void EQ_firstGuess_notAnswer() {
        LetterResult[] result = testGame.submitGuess("MIXER");
        assertEquals("Ggggg", GuessResultTest.getLetterResultArrayAsString(result));
        assertEquals(1, testGame.getGuessCount());
        assertGameIsNotOver();
    }

    @Test
    @DisplayName("GameState - BOUNDARY - last guess that isn't the answer")
    public void BNDRY_lastGuess_notAnswer() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 5, WordleImplementation.GameStatus.PLAYING);
        LetterResult[] result = testGame.submitGuess("MIXER");
        assertEquals("Ggggg", GuessResultTest.getLetterResultArrayAsString(result));
        assertEquals(6, testGame.getGuessCount());
        assertGameIsLoss();
    }

    @Test
    @DisplayName("GameState - BOUNDARY - last guess that is the answer")
    public void BNDRY_lastGuess_isAnswer() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 5, WordleImplementation.GameStatus.PLAYING);
        LetterResult[] result = testGame.submitGuess("MATCH");
        assertEquals("GGGGG", GuessResultTest.getLetterResultArrayAsString(result));
        assertEquals(6, testGame.getGuessCount());
        assertGameIsWin();
    }

    @Test
    @DisplayName("GameState - BOUNDARY - first guess that is the answer")
    public void BNDRY_firstGuess_isAnswer() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 0, WordleImplementation.GameStatus.PLAYING);
        LetterResult[] result = testGame.submitGuess("MATCH");
        assertEquals("GGGGG", GuessResultTest.getLetterResultArrayAsString(result));
        assertEquals(1, testGame.getGuessCount());
        assertGameIsWin();
    }

    @Test
    @DisplayName("GameState - EXCEPTION - bad guesses - too long")
    public void EXCPT_badGuess_Length_tooLong() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 0, WordleImplementation.GameStatus.PLAYING);
        assertThrows(IllegalWordException.class, () -> testGame.submitGuess("ANSWER"));
        assertEquals(0, testGame.getGuessCount(), "GuessCount incorrectly changed after a bad guess");
        assertGameIsNotOver();
    }

    @Test
    @DisplayName("GameState - EXCEPTION - bad guesses - too short")
    public void EXCPT_badGuess_Length_tooShort() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 0, WordleImplementation.GameStatus.PLAYING);
        assertThrows(IllegalWordException.class, () -> testGame.submitGuess("WORD"));
        assertEquals(0, testGame.getGuessCount(), "GuessCount incorrectly changed after a bad guess");
        assertGameIsNotOver();
    }

    @Test
    @DisplayName("GameState - EXCEPTION - bad guesses - not a word")
    public void EXCPT_badGuess_notAWord() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 0, WordleImplementation.GameStatus.PLAYING);
        assertThrows(IllegalWordException.class, () -> testGame.submitGuess("UFDSG"));
        assertEquals(0, testGame.getGuessCount(), "GuessCount incorrectly changed after a bad guess");
        assertGameIsNotOver();
    }

    @Test
    @DisplayName("GameState - EXCEPTION - game already over")
    public void EXCPT_badGuess_gameOver() {
        testGame = new WordleImplementation("MATCH", defaultGuessDictionary, 6, WordleImplementation.GameStatus.WON);
        assertThrows(GameAlreadyOverException.class, () -> testGame.submitGuess("UFDSG"));
        assertEquals(6, testGame.getGuessCount(), "GuessCount incorrectly changed after a bad guess");
        assertGameIsWin();
    }

    private void assertGameIsNotOver() {
        assertFalse(testGame.isGameOver());
        assertFalse(testGame.isLoss());
        assertFalse(testGame.isWin());
    }

    private void assertGameIsWin() {
        assertTrue(testGame.isGameOver());
        assertFalse(testGame.isLoss());
        assertTrue(testGame.isWin());
    }

    private void assertGameIsLoss() {
        assertTrue(testGame.isGameOver());
        assertTrue(testGame.isLoss());
        assertFalse(testGame.isWin());
    }
}