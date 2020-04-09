// Alan Li
// 04/09/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Letter Inventory
//
// LetterInventory will store the number of each letter in corresponding counters and can return the
// size of the data

public class LetterInventory{
    // By default there are 26 alphabetic letters(case insensitive)
    public static final int DEFAULT_CAPACITY = 26;

    // The number of each alphabetic letter in corresponding counter
    private int[] count;

    // The number of alphabetic letter in the data
    private int size;

    /**
     * store the number of alphabetic letters in corresponding counter
     * @param info the given string
     */
    public LetterInventory(String info){
        count = new int[DEFAULT_CAPACITY];
        info = info.toLowerCase();
        for (int i = 0; i <= info.length() - 1; i++){
            char letter = info.charAt(i);
            if (Character.isLetter(letter)){
                // store letter a at index 0, b at index 1, etc.
                count[letter - 'a']++;
                size++;
            }
        }
    }


    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * takes an character and return the occurrences of the character in the data
     * @param letter
     * @return
     */
    public int get(char letter){
        letter = Character.toLowerCase(letter);
        if (!Character.isLetter(letter)){
            throw new IllegalArgumentException("Passed a nonalphabetic character!");
        }
        return count[letter - 'a'];
    }

    public void set(char letter, int value){
        letter = Character.toLowerCase(letter);
        if (!Character.isLetter(letter) || value < 0){
            throw new IllegalArgumentException("Passed a nonalphabetic character!");
        }
        size += value - count[letter - 'a'];
        count[letter - 'a'] = value;
    }

    public String toString(){
        String list = "";
        for (int i = 0; i < DEFAULT_CAPACITY; i++){
            for (int j = 1; j <= count[i]; j++){
                list += (char)('a' + i);
            }
        }
        list = ("[" + list + "]");
        return list;
    }

    public LetterInventory add(LetterInventory other){
        LetterInventory combined = new LetterInventory("");
        for (int i = 0; i < DEFAULT_CAPACITY; i++){
            combined.count[i] = this.count[i] + other.count[i];
        }
        combined.size = this.size + other.size;
        return combined;
    }

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
