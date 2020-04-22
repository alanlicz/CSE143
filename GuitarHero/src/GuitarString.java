// Alan Li
// 04/16/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Guitar Hero
//
// GuitarString class will simulate the vibration of a string at given frequency
import java.util.*;

public class GuitarString {

    // The decay factor
    public static final double DECAY_FACTOR = 0.996;

    // Current ring buffer of the string
    private Queue<Double> ringBuffer;

    /**
     * Constructs a GuitarString using the given frequency
     * Initialize a ring buffer with size calculated using sample rate and given frequency
     * Fill the ring buffer with 0
     * @param frequency frequency of the guitarString
     * @exception IllegalArgumentException if frequency if less or equal than zero or the size of
     * the ring buffer is less than 2
     */
    public GuitarString(double frequency){
        int size = (int)Math.round(StdAudio.SAMPLE_RATE / frequency);
        if (frequency <= 0 || size < 2){
            throw new IllegalArgumentException();
        }
        ringBuffer = new LinkedList<Double>();
        for (int i = 1; i <= size; i++){
            ringBuffer.add(0.0);
        }
    }

    /**
     * Only for testing purposes
     * Construct a GuitarString
     * Initialize the ring buffer with the values in the array
     * @param init the array that will fill into the ring buffer
     * @exception IllegalArgumentException if the length of array is less than 2
     */

    public GuitarString(double[] init){
        if (init.length < 2){
            throw new IllegalArgumentException();
        }

        for (int i = 0; i <= init.length; i++){
            ringBuffer.add(init[i]);
        }
    }

    /**
     * Fill in the ring buffer with random number
     */
    public void pluck(){
        Random r = new Random();
        double randomNum;
        for (int i = 0; i < ringBuffer.size(); i++){
            ringBuffer.remove();
            // Generate number from -0.5 inclusive to 0.5 exclusive
            randomNum = r.nextDouble() - 0.5;
            ringBuffer.add(randomNum);
        }
    }

    /**
     * Use the Karplus-Strong update once, remove the first number in queue, and add the processd
     * element to the back of the queue
     */
    public void tic(){
        double number = ringBuffer.remove() + ringBuffer.peek();
        number = number * 0.5 * DECAY_FACTOR;
        ringBuffer.add(number);
    }

    /**
     * return the value at the front of the ring buffer
     * @return the value at the front of the ring buffer
     */
    public double sample(){
        return ringBuffer.peek();
    }

}
