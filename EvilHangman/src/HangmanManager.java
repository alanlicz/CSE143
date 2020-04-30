// Alan Li
// 04/30/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Evil Hangman
//
// HangmanManager class carry out the task that will administer the game
import java.util.*;

public class HangmanManager {

    // store the pattern and corresponding words
    private Map<String, Set<String>> patternMap;

    // the pattern that computer choose from the patternMap
    private String currentPattern;

    // the words that currently match the guess
    private Set<String> dicFilter;

    // the letters that user has guessed
    private Set<Character> guessLetters;

    // the remaining guesses that user has
    private int guessesRemain;

    /**
     * construct a HangmanManager class that create an empty patternMap and filter all the words with the chosen length
     * @param dictionary the file contains all the words, assume it is non-empty
     * @param length the word length user choose
     * @param max maximum guesses user can make
     * @exception IllegalArgumentException if length is smaller than one
     * @exception IllegalArgumentException if max guess is smaller than zero
     */
    public HangmanManager(Collection<String> dictionary, int length, int max){
        if (length < 1){
            throw new IllegalArgumentException();
        }
        if (max < 0){
            throw new IllegalArgumentException();
        }
        patternMap = new TreeMap<>();
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
        // map that has initial pattern and all the words
        patternMap.put(currentPattern, dicFilter);
    }

    /**
     * initialize current pattern
     * @param length the word length
     * @return the empty pattern
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
     * return the words in the current pattern
     * @return the words in the current pattern
     */
    public Set<String> words(){
        return patternMap.get(currentPattern);
    }

    /**
     * return how many guesses left
     * @return how many guesses left
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
     * return the current pattern, letters not yet been guessed appear as dash
     * @return the current pattern
     * @exception IllegalArgumentException if no words has the pattern
     */
    public String pattern(){
        if (patternMap.get(currentPattern).isEmpty()){
            throw new IllegalArgumentException();
        }
        return currentPattern;
    }

    /**
     * perform one guess
     * @param guess the letter user guessed
     * @return the occurrence that the letter has appeared in the word
     * @exception IllegalArgumentException if user ran out of guesses
     * @exception IllegalStateException if no pattern exist
     * @exception IllegalArgumentException if user has already guessed the letter
     */
    public int record(char guess){
        if (guessesRemain < 1){
            throw new IllegalStateException();
        }
        if (patternMap.get(currentPattern).isEmpty()){
            throw new IllegalStateException();
        }
        if (guessLetters.contains(guess)){
            throw new IllegalArgumentException();
        }
        int occurrence;
        guessLetters.add(guess);
        newMap(guess);
        currentPattern = makeChoice();
        occurrence = countOccurrence(currentPattern, guess);
        if (occurrence == 0){
            guessesRemain--;
        }
        return occurrence;
    }

    /**
     * erase the old patternMap and update to a new one
     * @param guess the letter that user guessed
     */
    private void newMap(char guess){
        dicFilter = patternMap.get(currentPattern);
        String subPattern;
        patternMap.clear();
        for (String word : dicFilter){
            subPattern = newPattern(word, guess, currentPattern);
            // add word to the existing pattern
            if (patternMap.containsKey(subPattern)){
                patternMap.get(subPattern).add(word);
            } else{
                Set<String> words = new TreeSet<>();
                words.add(word);
                patternMap.put(subPattern, words);
            }
        }

    }

    /**
     * make new patterns after user's guess
     * @param word the word that has the pattern
     * @param guess the letter user guessed
     * @param lastPattern the pattern before user's guess
     * @return the pattern after the guess
     */
    private String newPattern(String word, char guess, String lastPattern){
        String pattern = "";
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == guess){
                pattern += guess;
            } else{
                if (word.charAt(i) == lastPattern.charAt(i * 2)){
                    pattern += lastPattern.charAt(i * 2);
                }
                else{
                    pattern += "-";
                }
            }
            pattern += " ";
        }
        return pattern;
    }

    /**
     * select the best pattern
     * @return the best pattern
     */
    private String makeChoice(){
        int max = 0;
        String bestPattern = "";
        for (String pattern : patternMap.keySet()){
            Set<String> words = patternMap.get(pattern);
            // choose alphabetically first when there is a tie
            if (words.size() > max){
                max = words.size();
                bestPattern = pattern;
            }
        }
        return bestPattern;
    }

    /**
     * count the occurrence of the letter in the word
     * @param pattern the pattern that computer select
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
        return count;
    }

}
