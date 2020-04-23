// Alan Li
// 04/16/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Guitar Hero
//
// Guitar37 class will implement 37 strings of the Guitar interface

public class Guitar37 implements Guitar {
    // keyboard layout
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    // The array that stores all the strings
    private GuitarString[] guitarStringALl;

    // Count the times that tic is called
    private int currentTime;

    /**
     * Construct a Guitar37 class
     * Initialize an array that store the frequency of corresponding keys
     */
    public Guitar37(){
        currentTime = 0;
        guitarStringALl = new GuitarString[KEYBOARD.length()];
        double frequency = 0;
        for(int i = 0; i < guitarStringALl.length; i++){
            // Give the i-th character of the string frequency
            frequency = 440 * Math.pow(2, (i - 24.0) / 12.0);
            guitarStringALl[i] = new GuitarString(frequency);
        }
    }

    /**
     * pluck the guitar with the given pitch
     * @param pitch the pitch that guitar wants to play
     */
    @Override
    public void playNote(int pitch) {
        // Only play pitch that is larger than negative 24 inclusive and less than 12 inclusive
        if (pitch >= -24 && pitch <= 12){
            guitarStringALl[pitch + 24].pluck();
        }
    }

    /**
     * return true if the key has a corresponding string, return false if not
     * @param key the key that wants to be checked
     * @return true if the key has a corresponding string, false if not
     */
    @Override
    public boolean hasString(char key) {
        return KEYBOARD.indexOf(key) != -1;
    }

    /**
     * pluck the guitar with the string that correspond to the key
     * @param key plucks the guitar with string that correspond to the key
     * @exception IllegalArgumentException if the key cannot be plucked
     */
    @Override
    public void pluck(char key) {
        if (!hasString(key)){
            throw new IllegalArgumentException();
        }
        guitarStringALl[KEYBOARD.indexOf(key)].pluck();
    }

    /**
     * return a sample of all the strings in the array
     * @return a sample of all the strings in the array
     */
    @Override
    public double sample() {
        double sampleSum = 0;
        for (int i = 0; i < KEYBOARD.length(); i++){
            sampleSum += guitarStringALl[i].sample();
        }
        return sampleSum;
    }

    /**
     * call tic on all the strings in the array
     */
    @Override
    public void tic() {
        for (int i = 0; i < KEYBOARD.length(); i++){
            guitarStringALl[i].tic();
        }
        currentTime++;
    }

    /**
     * return the times that tic is called
     * @return the times that tic is called
     */
    @Override
    public int time() {
        return currentTime;
    }
}
