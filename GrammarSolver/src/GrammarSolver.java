// Alan Li
// 05/12/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment Grammar Solver
//
// GrammarSolver class generate random text based on the given grammar rules
import java.util.*;

public class GrammarSolver {

    // Store grammar rules
    private SortedMap<String, List<String[]>> grammarMap;

    /**
     * break apart the rules in BNF format into different non-terminals and link them with
     * corresponding values and rules
     * @param rules list of string that contains given grammar rules in BNF format
     * @exception IllegalArgumentException if the grammar rules is empty or there are two or more
     * entries for the same non-terminal
     */
    public GrammarSolver(List<String> rules){
        if (rules.isEmpty()){
            throw new IllegalArgumentException();
        }
        grammarMap = new TreeMap<>();
        for (String BNF : rules){
            String[] splitBNF = BNF.split("::=");
            if (grammarContains(splitBNF[0])){
                throw new IllegalArgumentException();
            }
            // \\| does not work in Intellij, changed to [|]
            String[] choices = splitBNF[1].split("[|]");
            List<String[]> rule = new ArrayList<>();
            for (String choice : choices) {
                rule.add(choice.trim().split("\\s+"));
            }
            grammarMap.put(splitBNF[0], rule);
        }
    }

    /**
     * return whether the passed symbol is a non-terminal
     * @param symbol the string that needs to be checked if it is a non-terminal, case is sensitive
     * @return true if the symbol is a non-terminal, false if not
     */
    public boolean grammarContains(String symbol){
        return grammarMap.containsKey(symbol);
    }

    /**
     * return a string representation of non-terminal symbols in the grammar
     * @return a string of non-terminal symbols as sorted and enclosed in square brackets,
     * comma separated
     */
    public String getSymbols(){
        return grammarMap.keySet().toString();
    }

    /**
     * return given times of the string from the symbol, strings are randomly generated
     * @param symbol non-terminals that need to generate string from, case is sensitive
     * @param times the times that the string needs to be generated
     * @return the array contains all the strings, each string has one space between each terminal
     * and there is no leading or trailing spaces
     * @exception IllegalArgumentException if times is less than zero or symbol is not a
     * non-terminal
     */
    public String[] generate(String symbol, int times){
        if (times < 0 || !grammarContains(symbol)){
            throw new IllegalArgumentException();
        }
        String[] sentences = new String[times];
        for (int i = 0; i < times; i++){
            sentences[i] = generate(symbol);
        }
        return sentences;
    }

    /**
     * return a string from the given symbol
     * @param symbol the non-terminal that the string needs to generate from, case is sensitive
     * @return string generate from the symbol, it has one space between each terminal
     * and there is no leading or trailing spaces
     */
    private String generate(String symbol){

        String result = "";
        if (!grammarContains(symbol)) {
            return symbol;
        } else{
            List<String[]> values = grammarMap.get(symbol);
            Random r = new Random();
            int ranNum = r.nextInt(values.size());
            String[] randomRules = values.get(ranNum);
            for (String word : randomRules) {
                result += generate(word) + " ";
            }
            return result.trim();
        }
    }
}
