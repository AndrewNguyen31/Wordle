package edu.virginia.cs.wordle;

import java.util.Scanner;

public class CommandLineWordle {
    private Scanner scanner;

    public static void main(String[] args) {
        CommandLineWordle play = new CommandLineWordle();
        play.run();
    }

    public void run() {
        System.out.println("Welcome To Wordle!");
        initializeScanner();
        preloadDefaultDictionaries();
        do {
            playGameOfWordle();
        } while (playAgain());
        finalizeWordle();
    }

    private void initializeScanner() {
        scanner = new Scanner(System.in);
    }

    private void preloadDefaultDictionaries() {
        System.out.println("Loading dictionaries...");
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        factory.loadDefaultDictionaries();
    }

    private void playGameOfWordle() {
        System.out.println("Here we go!");
        Wordle wordle = new WordleImplementation();

        while(!wordle.isGameOver()) {
            System.out.println("You have " + wordle.getRemainingGuesses() + " guesses remaining!");
            getAndHandleUserGuess(wordle);
        }
        generateWinOrLossMessage(wordle);
    }

    private boolean playAgain() {
        while (true) {
            String userResponse = prompt("Would you like to play again? (Y/N) > ");
            if (userResponse.toUpperCase().startsWith("Y")) {
                return true;
            } else if (userResponse.toUpperCase().startsWith("N")) {
                return false;
            } else {
                System.out.println("I'm sorry, that was not a valid input. Please try again.");
            }
        }
    }

    private void finalizeWordle() {
        System.out.println("Thank you for playing!");
        scanner.close();
    }

    private void getAndHandleUserGuess(Wordle wordle) {
        String guess = prompt("Enter a 5-letter word> ");
        try {
            LetterResult[] result = wordle.submitGuess(guess);
            System.out.println(getResultString(guess, result));
        } catch (IllegalWordException e) {
            System.out.println(guess + " is not a valid word!");
            System.out.println("Try again!");
        }
    }

    private void generateWinOrLossMessage(Wordle wordle) {
        if (wordle.isWin()) {
            System.out.println("Congratulations! You won in " + wordle.getGuessCount() + " guesses!");
        } else if (wordle.isLoss()) {
            System.out.println("Sorry, you are out of guesses... The word was " + wordle.getAnswer());
        }
    }

    private String prompt(String s) {
        System.out.print(s);
        return scanner.nextLine();
    }

    private String getResultString(String guess, LetterResult[] result) {
        StringBuilder wordWithColoredLetters = new StringBuilder();
        for (int letterIndex = 0; letterIndex < result.length; letterIndex++) {
            char guessLetter = guess.charAt(letterIndex);
            LetterResult letterResult = result[letterIndex];
            getColorfulPrintMessage(wordWithColoredLetters, guessLetter, letterResult);
        }
        return wordWithColoredLetters.toString();
    }

    private void getColorfulPrintMessage(StringBuilder wordWithColoredLetters, char guessLetter, LetterResult letterResult) {
        String coloredLetterResult = getColorfulLetterResult(guessLetter, letterResult);
        wordWithColoredLetters.append(coloredLetterResult);
    }

    private String getColorfulLetterResult(char guessLetter, LetterResult letterResult) {
        return switch (letterResult) {
            case GRAY -> getGrayLetter(guessLetter);
            case GREEN -> getGreenLetter(guessLetter);
            case YELLOW -> getYellowLetter(guessLetter);
        };
    }

    private String getGrayLetter(char guessLetter) {
        return "\033[1;47m" + guessLetter + "\033[0m";
    }

    private String getGreenLetter(char guessLetter) {
        return "\033[1;42m" + guessLetter + "\033[0m";
    }

    private String getYellowLetter(char guessLetter) {
        return "\033[1;43m" + guessLetter + "\033[0m";
    }
}
