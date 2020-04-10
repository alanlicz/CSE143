// Alan Li
// 04/09/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Letter Inventory
//
// LetterInventory will store the number of each letter in corresponding counters and can return the
// size of the string

public class LetterInventory{
    // By default there are 26 alphabetic letters(case insensitive)
    public static final int DEFAULT_CAPACITY = 26;

    // The number of each alphabetic letter in corresponding counter
    private int[] count;

    // The number of alphabetic letter in the string
    private int size;

    /**
     * store the number of alphabetic letters in corresponding counter
     * @param info the given string
     */
    public LetterInventory(String info){
        count = new int[DEFAULT_CAPACITY];
        info = info.toLowerCase();
        for (int i = 0; i < info.length(); i++){
            char letter = info.charAt(i);
            if (Character.isLetter(letter)){
                // store the number of letter 'a' at index 0, the number of 'b' at index 1, etc.
                count[letter - 'a']++;
                size++;
            }
        }
    }

    /**
     * return the sum of all the numbers in array count combined
     * @return return the sum of all letters in the string
     */
    public int size(){
        return size;
    }

    /**
     * check if the string is empty
     * @return true if the string is empty, false if string is not empty
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * takes an character and return how many times character appear in the string
     * @param letter the character that needs to be checked the times that appear in the string
     * @exception IllegalArgumentException if passed a non-alphabetic letter
     * @return return the number of times that letter appears in the string
     */
    public int get(char letter){
        if (!Character.isLetter(letter)){
            throw new IllegalArgumentException("Passed a non-alphabetic character!");
        }
        letter = Character.toLowerCase(letter);
        return count[letter - 'a'];
    }

    /**
     * set the given letter counter to the given number
     * @param letter the letter whose counter needs to be modified
     * @param value modify the counter to the given value
     * @exception IllegalArgumentException if the character is non-alphabetic
     * @exception IllegalArgumentException if the given value is less than zero
     */
    public void set(char letter, int value){
        letter = Character.toLowerCase(letter);
        if (!Character.isLetter(letter)){
            throw new IllegalArgumentException("Passed a non-alphabetic character!");
        }
        if (value < 0){
            throw new IllegalArgumentException(("The given value is illegal!"));
        }
        size += value - count[letter - 'a'];
        count[letter - 'a'] = value;
    }

    /**
     * return a string of all the letters in lowercase in the string with the sorted order(from a to
     * z), surrounded by square brackets
     * @return square brackets containing all the letters in the string in lowercase and sorted
     * order
     */
    public String toString(){
        String list = "";
        for (int i = 0; i < DEFAULT_CAPACITY; i++){
            for (int j = 0; j < count[i]; j++){
                list += (char)('a' + i);
            }
        }
        list = ("[" + list + "]");
        return list;
    }

    /**
     * construct a new LetterInventory type which contains both letters in the existing
     * LetterInventory and given LetterInventory
     * @param other the given LetterInventory
     * @return a new LetterInventory that is sum of this and other LetterInventory
     */
    public LetterInventory add(LetterInventory other){
        LetterInventory combined = new LetterInventory("");
        for (int i = 0; i < DEFAULT_CAPACITY; i++){
            combined.count[i] = this.count[i] + other.count[i];
        }
        combined.size = this.size + other.size;
        return combined;
    }

    /**
     * construct a new LetterInventory which will be the result of this LetterInventory subtract
     * other LetterInventory
     * @param other the given LetterInventory which this LetterInventory will subtract from
     * @return return the new LetterInventory after the subtract or null if any of the counters
     * become negative after subtraction
     */
    public LetterInventory subtract(LetterInventory other){
        LetterInventory reduced = new LetterInventory("");
        for (int i = 0; i < DEFAULT_CAPACITY; i++){
            reduced.count[i] = this.count[i] - other.count[i];
            if (reduced.count[i] < 0) {
                return null;
            }
            reduced.size += reduced.count[i];
        }
        return reduced;
    }
}
