package edu.virginia.cs.wordle;

public enum LetterResult {
    /**
     * GRAY result, this letter isn't in the word
     */
    GRAY {
        @Override
        public String toString() {
            return ("\033[0;37m" + name() + "\033[0m");
        }
    },

    /**
     * GREEN result, this letter is in the word at this location
     */
    GREEN {
        @Override
        public String toString() {
            return ("\033[0;32m" + name() + "\033[0m");
        }
    },

    /**
     * YELLOW result, this letter is in the word, but not at this location
     */
    YELLOW {
        @Override
        public String toString() {
            return ("\033[0;33m" + name() + "\033[0m");
        }
    }

}
