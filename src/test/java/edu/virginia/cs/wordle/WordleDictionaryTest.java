package edu.virginia.cs.wordle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordleDictionaryTest {
    private static final String ONE_WORD_DICTIONARY_FILENAME = "/wordle/test_dictionaries/one_word_dictionary.txt";

    WordleDictionary testDictionary;
    Set<String> mockWords;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setupTestDictionary() {
        mockWords = (Set<String>) mock(Set.class);
        testDictionary = new WordleDictionary();
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE - one word dictionary test")
    public void testOneWordDictionary() {
        InputStream inputStream = WordleDictionaryTest.class.getResourceAsStream(ONE_WORD_DICTIONARY_FILENAME);
        testDictionary.addWordsFromInputStream(inputStream);
        assertEquals(1, testDictionary.getDictionarySize());
        assertTrue(testDictionary.containsWord("BALDY"));
    }

    @Test
    @DisplayName("WordleDictionary - TAs: if this test fails, let me know - post in #hw2-rubric-tests-issues")
    public void test_WordleDictionary_Mock() {
        useMockSetForDictionary();
        when(mockWords.size()).thenReturn(3);
        when(mockWords.contains("BASIC")).thenReturn(true);
        when(mockWords.contains("QXZYZ")).thenReturn(false);

        assertEquals(3, mockWords.size());
        assertTrue(mockWords.contains("BASIC"));
        assertFalse(mockWords.contains("QXZYZ"));
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: contains word didn't work with a mocked set - true expected")
    public void test_EQ_Mock_ContainsWord_True() {
        useMockSetForDictionary();
        when(mockWords.contains("BASIC")).thenReturn(true);
        assertTrue(testDictionary.containsWord("BASIC"));
        verify(mockWords).contains("BASIC");
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: contains word didn't work with a mocked set - false expected")
    public void test_EQ_Mock_ContainsWord_False() {
        useMockSetForDictionary();
        when(mockWords.contains("QZXYZ")).thenReturn(false);
        assertFalse(testDictionary.containsWord("QZXYZ"));
        verify(mockWords).contains("QZXYZ");
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: isLegalWordleWord - MUSIC")
    public void test_EQ_isLegalWordleWord_true_BASIC() {
        assertTrue(testDictionary.isLegalWordleWord("MUSIC"));
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: isLegalWordleWord - ABCDE")
    public void test_EQ_isLegalWordleWord_true_ABCDE() {
        assertTrue(testDictionary.isLegalWordleWord("ABCDE"));
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: isLegalWordleWord - ZZZZZ")
    public void test_EQ_isLegalWordleWord_true_ZZZZZ() {
        assertTrue(testDictionary.isLegalWordleWord("ZZZZZ"));
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: isLegalWordleWord - 12345")
    public void test_EQ_isLegalWordleWord_false_NUMBER() {
        assertFalse(testDictionary.isLegalWordleWord("12345"));
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: isLegalWordleWord - !#*$&")
    public void test_EQ_isLegalWordleWord_false_SPECIAL_CHARACTERS() {
        assertFalse(testDictionary.isLegalWordleWord("!#*$&"));
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: lowercase - music - true")
    public void test_BNDRY_isLegalWordleWord_true_music_lowercase() {
        assertTrue(testDictionary.isLegalWordleWord("music"));
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: mixedcase - PiZzA - true")
    public void test_BNDRY_isLegalWordleWord_true_mUsIc_mixed_case() {
        assertTrue(testDictionary.isLegalWordleWord("PiZzA"));
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: whitespace in middle of word - ha ha - false")
    public void test_BNDRY_isLegalWordleWord_true_whitespace() {
        assertFalse(testDictionary.isLegalWordleWord("ha ha"));
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: tests false for null")
    public void test_BNDRY_isLegalWordleWord_null() {
        assertFalse(testDictionary.isLegalWordleWord(null));
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: tests false for bad length inputs")
    public void test_BNDRY_isLegalWordleWord_BAD_LENGTH() {
        assertFalse(testDictionary.isLegalWordleWord(null));
        assertFalse(testDictionary.isLegalWordleWord("MISS"));
        assertFalse(testDictionary.isLegalWordleWord("NOGOOD"));
    }

    @Test
    @DisplayName("WordleDictionary - EQUIVALENCE: addWord - MUSIC - mocked")
    public void test_EQ_addWord_Basic() {
        useMockSetForDictionary();
        testDictionary.addWord("MUSIC");
        verify(mockWords).add("MUSIC");
    }

    @Test
    @DisplayName("WordleDictionary - EXCEPTION: addWord - FAILED - mocked")
    public void test_EXCP_addWord_TOO_LONG() {
        useMockSetForDictionary();
        assertThrows(IllegalWordException.class,
                () -> testDictionary.addWord("FAILED"));
        verify(mockWords, never()).add(any());
    }

    @Test
    @DisplayName("WordleDictionary - EXCEPTION: addWord - FAIL - mocked")
    public void test_EXCP_addWord_TOO_SHORT() {
        useMockSetForDictionary();
        assertThrows(IllegalWordException.class,
                () -> testDictionary.addWord("FAIL"));
        verify(mockWords, never()).add(any());
    }

    @Test
    @DisplayName("WordleDictionary - EXCEPTION: addWord with non letters - AB12!")
    public void test_EXCP_addWord_non_letters() {
        useMockSetForDictionary();
        assertThrows(IllegalWordException.class,
                () -> testDictionary.addWord("AB12!"));
        verify(mockWords, never()).add(any());
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: size should be zero before adding any words")
    public void test_BNDRY_size_initial() {
        assertEquals(0, testDictionary.getDictionarySize());
    }

    @Test
    @DisplayName("WordleDictionary - BOUNDARY: size after mocking added words")
    public void test_BNDRY_size_mocked() {
        useMockSetForDictionary();
        when(mockWords.size()).thenReturn(2);
        assertEquals(2, testDictionary.getDictionarySize());
    }


    private void useMockSetForDictionary() {
        testDictionary = new WordleDictionary(mockWords);
    }


}
