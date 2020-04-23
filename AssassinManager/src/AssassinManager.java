// Alan Li
// 04/22/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Assassin Manager
//
// AssassinManager class carry out the task that will administer the game
import java.util.*;

public class AssassinManager {

    // Linked list that contains the people in the kill ring
    private AssassinNode killRing;

    // Linked list that contains the people in the graveyard
    private AssassinNode graveyard;

    /**
     * Initialize a new assassin manager and construct a kill ring that contains every person
     * @exception NullPointerException if no one is in the list names
     * @param names use names to construct the list kill ring
     */
    public AssassinManager(List<String> names){
        if (names.isEmpty()){
            throw new NullPointerException();
        }
        // Create the first node in the kill list
        killRing = new AssassinNode(names.get(0));
        AssassinNode current = killRing;
        for (int i = 1; i < names.size(); i++){
            current.next = new AssassinNode(names.get(i));
            current = current.next;
        }
    }

    /**
     * print person in the kill ring with format
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
     * print person in the graveyard with format
     * The person that is killed first will be the last to print out
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
     * @param name need to be checked if is in the kill ring
     * @return true if the name is in the kill ring, false if not
     */
    public boolean killRingContains(String name){
        name = name.toLowerCase();
        if (killRing.name.equalsIgnoreCase(name)){
            return true;
        }
        AssassinNode current = killRing;
        while (current.next != null){
            if (current.next.name.equalsIgnoreCase(name)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * return if the person is dead
     * @param name need to be checked if is in the dead
     * @return true if the name is in the dead, false if not
     */
    public boolean graveyardContains(String name) {
        name = name.toLowerCase();
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
     * check if the game has ended
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

        String winner;
        if (killRing != null){
            winner = killRing.name;
        }else {
            return null;
        }
        return winner;
    }

    /**
     * kill one person whose name is passed
     * @param name the person that needs to be killed
     * @exception IllegalStateException if the game has ended
     * @exception IllegalArgumentException if the person is already dead
     */
    public void kill(String name){
        AssassinNode currentAssassin = killRing;
        AssassinNode currentGrave = graveyard;
        if (gameOver()) {
            throw new IllegalStateException();
        }
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
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
