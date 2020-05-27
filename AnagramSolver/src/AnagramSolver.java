// Alan Li
// 05/21/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment AnagramSolver
//
// AnagramSolver prints all anagram text of a word or phrase from given dictionary
import java.util.*;


public class AnagramSolver {

    private Map<String, LetterInventory> anagramMap;

    private List<String> originalDic;

    /**
     * store all the letters and corresponding word, the dictionary remains unchanged
     * @param dictionary contains all the words, assume that is is non-empty dictionary contains no
     *                   duplicates or empty element
     */
    public AnagramSolver(List<String> dictionary){
        anagramMap = new HashMap<>();
        originalDic = dictionary;
        for (String word : dictionary){
            anagramMap.put(word, new LetterInventory(word));
        }
    }

    /**
     * print all the combination of anagrams text up to max words, or unlimited if max is zero,
     * each anagram is printed on a new line. If result is empty it will print []
     * @param text store all the letters from the word or phrase, case is insensitive
     * @param max the max number of words that needs to print out, print unlimited words if max is 0
     * @exception IllegalArgumentException if max is less than zero
     */
    public void print(String text, int max){
        if (max < 0){
            throw new IllegalArgumentException();
        }
        print(new LetterInventory(text), max, originalDic, new ArrayList<>());
    }

    /**
     * print all the combination of anagrams text up to max words, or unlimited if max is
     * zero, each anagram is printed on a new line. If result is empty it will print []
     * @param letters store all the letters from a word or phrase, case is insensitive
     * @param max the max words in anagram text
     * @param dictionary store all the words
     * @param listSoFar store the current anagram text
     */
    private void print(LetterInventory letters,  int max,
                       List<String> dictionary, List<String> listSoFar){
        if (letters.isEmpty()){
            System.out.println(listSoFar);
        }
        List<String> result = new ArrayList<>();
        for (String word : dictionary){
            if (letters.subtract(anagramMap.get(word)) != null){
                result.add(word);
            }
        }
        if (max != listSoFar.size() || max == 0){
            for (String word : result){
                listSoFar.add(word);
                print(letters.subtract(anagramMap.get(word)), max, result, listSoFar);
                listSoFar.remove(listSoFar.size() - 1);
            }
        }
    }
}
