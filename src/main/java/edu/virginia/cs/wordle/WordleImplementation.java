package edu.virginia.cs.wordle;

public class WordleImplementation implements Wordle {
    public static final int MAX_GUESSES = 6;
    private String answer;
    private int guessCount;
    private GameStatus gameStatus;
    private WordleDictionary legalGuessDictionary;


    public WordleImplementation(String answer, WordleDictionary dictionary) {
        this(answer, dictionary, 0, GameStatus.PLAYING);
    }

    protected WordleImplementation(String answer, WordleDictionary dictionary, int guessCount, GameStatus status) {
        this.answer = answer.toUpperCase();
        this.legalGuessDictionary = dictionary;
        this.guessCount = guessCount;
        this.gameStatus = status;
        if (0 == legalGuessDictionary.getDictionarySize()) {
            throw new EmptyDictionaryException("Error: Cannot play Wordle with an Empty Dictionary");
        }
        if (!legalGuessDictionary.containsWord(answer)) {
            throw new IllegalWordException(
                    "Created a game with an illegal answer not found in the dictionary: " + answer);
        }
    }

    public WordleImplementation(String answer) {
        this(answer, getDefaultGuessesDictionary(), 0, GameStatus.PLAYING);
    }

    private static WordleDictionary getDefaultGuessesDictionary() {
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        return factory.getDefaultGuessesDictionary();
    }

    public WordleImplementation() {
        this(getRandomAnswerFromDefaultDictionary(), getDefaultGuessesDictionary(), 0, GameStatus.PLAYING);
    }

    private static String getRandomAnswerFromDefaultDictionary() {
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        WordleDictionary answersDictionary = factory.getDefaultAnswersDictionary();
        return answersDictionary.getRandomWord();
    }

    @Override
    public boolean isGameOver() {
        return GameStatus.PLAYING != gameStatus;
    }

    @Override
    public boolean isWin() {
        return GameStatus.WON == gameStatus;
    }

    @Override
    public boolean isLoss() {
        return GameStatus.LOST == gameStatus;
    }

    @Override
    public int getGuessCount() {
        return guessCount;
    }

    @Override
    public int getRemainingGuesses() {
        return MAX_GUESSES - guessCount;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public LetterResult[] submitGuess(String guess) {
        guess = guess.toUpperCase();
        verifyGameStateAndGuess(guess);
        guessCount++;
        checkIfGameEnded(guess);
        return getGuessResult(guess);
    }

    private void verifyGameStateAndGuess(String guess) {
        if (isGameOver()) {
            throw new GameAlreadyOverException("Error: Game is already over!");
        }
        if (guess == null || !legalGuessDictionary.containsWord(guess)) {
            throw new IllegalWordException("Error: " + guess + " is not a valid guess");
        }
    }

    private void checkIfGameEnded(String guess) {
        if (guess.equals(answer)) {
            gameStatus = GameStatus.WON;
        } else if (guessCount == MAX_GUESSES) {
            gameStatus = GameStatus.LOST;
        }
    }

    private LetterResult[] getGuessResult(String guess) {
        GuessResult guessResult = new GuessResult();
        guessResult.setGuess(guess);
        guessResult.setAnswer(answer);
        return guessResult.getGuessResult();
    }

    protected enum GameStatus {
        PLAYING, WON, LOST;
    }

}
