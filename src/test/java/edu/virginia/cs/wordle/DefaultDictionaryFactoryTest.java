package edu.virginia.cs.wordle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultDictionaryFactoryTest {
    DefaultDictionaryFactory testFactory;

    @BeforeEach
    public void setupTestFactory() {
        testFactory = new DefaultDictionaryFactory();
    }

    @Test
    @DisplayName("DefaultDictionaryFactory - EQUIVALENCE - Default Guesses Dictionary not null and size")
    public void testGetDefaultGuessesDictionaryNotNull() {
        WordleDictionary defaultGuessesDictionary = testFactory.getDefaultGuessesDictionary();
        assertNotNull(defaultGuessesDictionary);
        assertEquals(15920, defaultGuessesDictionary.getDictionarySize());
    }

    @Test
    @DisplayName("DefaultDictionaryFactory - EQUIVALENCE - Default Answers Dictionary not null and size")
    public void testGetDefaultAnswersDictionary() {
        WordleDictionary defaultAnswersDictionary = testFactory.getDefaultAnswersDictionary();
        assertNotNull(defaultAnswersDictionary);
        assertEquals(2315, defaultAnswersDictionary.getDictionarySize());
    }
}
