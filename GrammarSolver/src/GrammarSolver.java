import java.util.*;

public class GrammarSolver {

    // Store grammar rules
    private SortedMap<String, String[]> grammarMap;

    public GrammarSolver(List<String> rules){
        if (rules.isEmpty()){
            throw new IllegalArgumentException();
        }
        grammarMap = new TreeMap<>();
        for (String BNF : rules){
            String[] splitRule = BNF.split("::=");
            if (grammarContains(splitRule[0])){
                throw new IllegalArgumentException();
            }
            String[] rule = splitRule[1].split("\\|");
            grammarMap.put(splitRule[0], rule);
        }
    }

    public boolean grammarContains(String symbol){
        return grammarMap.containsKey(symbol);
    }

    public String getSymbols(){
        return grammarMap.keySet().toString();
    }

    public String[] generate(String symbol, int times){
        if (times < 0 || !grammarContains(symbol)){
            throw new IllegalArgumentException();
        }
        String[] sentences = new String[times];
        for (int i = 0; i < times; i++){
            sentences[i] = generate(symbol).trim();
        }
        return new String[0];
    }

    private String generate(String symbol){
        String[] rules = grammarMap.get(symbol);
        String result = "";
        Random r = new Random();
        int ranNum = r.nextInt(rules.length);
        for (String word : rules){
            result += generate(word);
        }
        return result;
    }
    
}
