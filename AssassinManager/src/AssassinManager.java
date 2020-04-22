import java.util.*;

public class AssassinManager {

    // Reference to the front node of the kill ring
    private AssassinNode killRing;

    // Reference to the node of graveyard
    private AssassinNode graveyard;

    public AssassinManager(List<String> names){
        if (names.isEmpty()){
            throw new NullPointerException();
        }
        killRing = new AssassinNode(names.get(0));
        AssassinNode current = killRing;
        for (int i = 1; i < names.size(); i++){
            current.next = new AssassinNode(names.get(i));
            current = current.next;
        }

    }

    public void printKillRing(){
        AssassinNode current = killRing;
        while (current.next != null){
            System.out.println("    " + current.name + " is stalking " + current.next.name);
            current = current.next;
        }
        System.out.println("    " + current.name + " is stalking " + killRing.name);
    }

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

    public boolean gameOver(){
        return killRing.next == null;
    }

    public String winner(){

        String winner = "";
        if (killRing != null){
            winner = killRing.name;
        }else {
            winner = null;
        }
        return winner;
    }

    public void kill(String name){
        AssassinNode currentAssassin = killRing;
        AssassinNode currentGrave = graveyard;
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        } else if (gameOver()) {
            throw new IllegalStateException();
        }
        if (currentAssassin.name.equalsIgnoreCase(name)) {
            graveyard = currentAssassin;
            while (currentAssassin.next != null){
                currentAssassin = currentAssassin.next;
            }
            killRing = killRing.next;
        } else {
            while (!currentAssassin.next.name.equalsIgnoreCase(name)) {
                currentAssassin = currentAssassin.next;
            }
            graveyard = currentAssassin.next;
            currentAssassin.next = currentAssassin.next.next;
        }
        graveyard.next = currentGrave;
        graveyard.killer = currentAssassin.name;
    }


}
