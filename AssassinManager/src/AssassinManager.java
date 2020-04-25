// Alan Li
// 04/22/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Assassin Manager
//
// AssassinManager class carry out the task that will administer the game
import java.util.*;

public class AssassinManager {

    // front node of kill ring
    private AssassinNode killRing;

    // front node of the graveyard
    private AssassinNode graveyard;

    /**
     * Initialize a new assassin manager and construct a kill ring
     * @exception IllegalArgumentException if names is empty
     * @param names use names to construct the kill ring, names are non-empty, non-null
     *              and no repeated names
     */
    public AssassinManager(List<String> names){
        if (names.isEmpty()){
            throw new IllegalArgumentException();
        }
        // Create the first node in the kill ring
        killRing = new AssassinNode(names.get(0));
        AssassinNode current = killRing;
        for (int i = 1; i < names.size(); i++){
            current.next = new AssassinNode(names.get(i));
            current = current.next;
        }
    }

    /**
     * print people in the kill ring with format
     */
    public void printKillRing(){
        AssassinNode current = killRing;
        while (current.next != null){
            System.out.println("    " + current.name + " is stalking " + current.next.name);
            current = current.next;
        }
        System.out.println("    " + current.name + " is stalking " + killRing.name);
    }

    /**
     * print people in the graveyard with format
     * The person that is killed most recent will be the first to print out
     */
    public void printGraveyard(){
        AssassinNode current = graveyard;
        String output = "";
        while(current != null) {
            output += "    " + current.name + " was killed by " + current.killer + "\n";
            current = current.next;
        }
        if (!output.equals("")){
            System.out.print(output);
        }
    }

    /**
     * return if the person is in the kill ring
     * @param name check name if it is in the kill ring, case is insensitive
     * @return true if the name is in the kill ring, false if not
     */
    public boolean killRingContains(String name){
        AssassinNode current = killRing;
        while (current != null){
            if (current.name.equalsIgnoreCase(name)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * return if the person is dead
     * @param name check name if is in the graveyard, case is insensitive
     * @return true if the name is in graveyard, false if not
     */
    public boolean graveyardContains(String name) {
        AssassinNode current = graveyard;
        while (current != null) {
            if (graveyard.name.equalsIgnoreCase(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * return if the game has ended
     * @return true if the game has ended, false if not
     */
    public boolean gameOver(){
        return killRing.next == null;
    }

    /**
     * return the winner
     * @return the person who wins the game
     */
    public String winner(){
        if (!gameOver()){
            return null;
        }
        return killRing.name;
    }

    /**
     * kill one person if the game has not ended or the person exist
     * @param name the person that needs to be killed
     * @exception IllegalStateException if the game has ended or game has ended and the person is
     * already dead
     * @exception IllegalArgumentException if the person is already dead
     */
    public void kill(String name){
        if (gameOver()){
            throw new IllegalStateException();
        }
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
        AssassinNode currentAssassin = killRing;
        AssassinNode currentGrave = graveyard;
        // check if the victim is the first person in the kill ring
        if (currentAssassin.name.equalsIgnoreCase(name)) {
            graveyard = currentAssassin;
            // find the killer
            while (currentAssassin.next != null){
                currentAssassin = currentAssassin.next;
            }
            // remove the person from kill ring
            killRing = killRing.next;
        } else {
            while (!currentAssassin.next.name.equalsIgnoreCase(name)) {
                currentAssassin = currentAssassin.next;
            }
            graveyard = currentAssassin.next;
            // remove the person from kill ring
            currentAssassin.next = currentAssassin.next.next;
        }
        // link the victim with graveyard
        graveyard.next = currentGrave;
        graveyard.killer = currentAssassin.name;
    }
}
