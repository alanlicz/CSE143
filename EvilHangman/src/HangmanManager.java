// Alan Li
// 04/30/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Evil Hangman
//
// HangmanManager class carry out the task that will administer the game
import java.util.*;

public class HangmanManager {

    // the pattern that match user's guess
    private String currentPattern;

    // the words that currently match the pattern
    private Set<String> dicFilter;

    // the letters that user has guessed
    private Set<Character> guessLetters;

    // the remaining guesses that user has
    private int guessesRemain;

    /**
     * construct a HangmanManager class that create a pattern with dash and space in between and
     * filter all the words with the chosen length
     * @param dictionary contains all words, assume it is non-empty and all lower cases
     * @param length the word length user choose
     * @param max maximum incorrect guesses user can make
     * @exception IllegalArgumentException if length is smaller than one
     * @exception IllegalArgumentException if max guess is smaller than zero
     */
    public HangmanManager(Collection<String> dictionary, int length, int max){
        if (length < 1 || max < 0){
            throw new IllegalArgumentException();
        }
        currentPattern = initialize(length);
        dicFilter = new TreeSet<>();
        guessLetters = new TreeSet<>();
        guessesRemain = max;

        // filter all the words
        for (String word : dictionary){
            if (word.length() == length) {
                dicFilter.add(word);
            }
        }
    }

    /**
     * initialize current pattern with dash and space in between
     * @param length the word length
     * @return the pattern with dash and space in between
     */
    private String initialize(int length){
        String empty = "";
        for (int i = 0; i < length; i++){
            empty += "- ";
        }
        // remove the last space
        empty = empty.trim();
        return empty;
    }

    /**
     * return the words has the current pattern
     * @return the words has the current pattern
     */
    public Set<String> words(){
        return dicFilter;
    }

    /**
     * return how many incorrect guesses left
     * @return how many incorrect guesses left
     */
    public int guessesLeft(){
        return guessesRemain;
    }

    /**
     * return the letter user has guessed
     * @return the letter user has guessed
     */
    public Set<Character> guesses(){
        return guessLetters;
    }

    /**
     * return the current pattern with space in between each letter,
     * letters not yet been guessed appear as dash
     * @return the current pattern
     * @exception IllegalArgumentException if no words has the pattern
     */
    public String pattern(){
        if (dicFilter.isEmpty()){
            throw new IllegalArgumentException();
        }
        return currentPattern;
    }

    /**
     * perform one guess and return the times the guessed letter appear in the word
     * @param guess the letter user guessed
     * @return the occurrence that the letter has appeared in the word
     * @exception IllegalStateException if user ran out of incorrect guesses
     * @exception IllegalStateException if no pattern exists
     * @exception IllegalArgumentException if user has already guessed the letter
     */
    public int record(char guess){
        if (guessesRemain < 1 || dicFilter.isEmpty()){
            throw new IllegalStateException();
        }
        if (guessLetters.contains(guess)){
            throw new IllegalArgumentException();
        }

        Map<String, Set<String>> patternMap = new TreeMap<>();
        int occurrence;
        guessLetters.add(guess);
        String subPattern;

        for (String word : dicFilter){
            subPattern = newPattern(word);
            // add word to the existing pattern or create new pattern in the patternMap
            if (patternMap.containsKey(subPattern)){
                patternMap.get(subPattern).add(word);
            } else{
                Set<String> words = new TreeSet<>();
                words.add(word);
                patternMap.put(subPattern, words);
            }
        }

        currentPattern = makeChoice(patternMap);
        occurrence = countOccurrence(currentPattern, guess);
        return occurrence;
    }


    /**
     * make new patterns after user's guess
     * @param word the word that has the pattern
     * @return the pattern after the guess
     */
    private String newPattern(String word){
        String pattern = "";
        for (int i = 0; i < word.length(); i++){
            if (guessLetters.contains(word.charAt(i))){
                pattern += word.charAt(i);
            } else{
                pattern += "-";
            }
            pattern += " ";
        }
        pattern = pattern.trim();
        return pattern;
    }

    /**
     * select the best pattern and choose alphabetically first if there is a tie
     * @param patternMap stores the pattern and corresponding words
     * @return the best pattern
     */
    private String makeChoice(Map<String, Set<String>> patternMap){
        int max = 0;
        String bestPattern = "";
        for (String pattern : patternMap.keySet()){
            Set<String> words = patternMap.get(pattern);
            // choose alphabetically first when there is a tie
            if (words.size() > max){
                dicFilter = words;
                max = words.size();
                bestPattern = pattern;
            }
        }
        return bestPattern;
    }

    /**
     * count the occurrence of the letter in the word and deduct incorrect guess remains if guessed
     * the wrong letter
     * @param pattern the pattern that match the word
     * @param guess the letter that user guessed
     * @return the occurrence of the letter in the word
     */
    public int countOccurrence(String pattern, char guess){
        int count = 0;
        for (int i = 0; i < (pattern.length() + 1) / 2; i++){
            if (pattern.charAt(2* i) == guess){
                count++;
            }
        }
        if (count == 0){
            guessesRemain--;
        }
        return count;
    }

}
