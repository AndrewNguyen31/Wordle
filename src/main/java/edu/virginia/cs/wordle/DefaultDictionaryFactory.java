package edu.virginia.cs.wordle;

import java.io.InputStream;

public class DefaultDictionaryFactory {
    private static final String DEFAULT_GUESSES_DICTIONARY_FILENAME = "/wordle/dictionaries/dictionary.txt";
    private static final String DEFAULT_ANSWERS_DICTIONARY_FILENAME = "/wordle/dictionaries/wordle_answers.txt";

    private WordleDictionary defaultGuessesDictionary;
    private WordleDictionary defaultAnswersDictionary;

    public WordleDictionary getDefaultGuessesDictionary() {
        if (ifDictionaryNotCreatedYet(defaultGuessesDictionary)) {
            defaultGuessesDictionary = new WordleDictionary();
            InputStream inputStream = getResourceAsInputStream(DEFAULT_GUESSES_DICTIONARY_FILENAME);
            addInputStreamWordsToDictionary(inputStream, defaultGuessesDictionary);
        }
        return defaultGuessesDictionary;
    }

    public WordleDictionary getDefaultAnswersDictionary() {
        if (ifDictionaryNotCreatedYet(defaultAnswersDictionary)) {
            defaultAnswersDictionary = new WordleDictionary();
            InputStream inputStream = getResourceAsInputStream(DEFAULT_ANSWERS_DICTIONARY_FILENAME);
            addInputStreamWordsToDictionary(inputStream, defaultAnswersDictionary);
        }
        return defaultAnswersDictionary;
    }

    /**
     * Front-loads reading the default dictionaries into memory
     */
    public void loadDefaultDictionaries() {
        getDefaultGuessesDictionary();
        getDefaultAnswersDictionary();
    }

    private boolean ifDictionaryNotCreatedYet(WordleDictionary dictionary) {
        return null == dictionary;
    }

    private void addInputStreamWordsToDictionary(InputStream inputStream, WordleDictionary dictionary) {
        dictionary.addWordsFromInputStream(inputStream);
    }

    private static InputStream getResourceAsInputStream(String filename) {
        return DefaultDictionaryFactory.class.getResourceAsStream(filename);
    }
}
