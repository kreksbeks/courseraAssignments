import java.util.HashMap;
import java.util.Map;

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    //todo optimize to read distances once for each pair
    public String outcast(String[] nouns) {
        Map<String, Integer> distances = new HashMap<String, Integer>();
        for (String n1 : nouns) {
            distances.put(n1, 0);
            for (String n2 : nouns) {
                int distance = n1.equals(n2) ? 0 : wordnet.distance(n1, n2);
                Integer n1Dist = distances.get(n1);
                distances.put(n1, n1Dist + distance);
            }
        }

        String outcast = null;
        int maxDistance = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> e : distances.entrySet()) {
            Integer distance = e.getValue();
            if (maxDistance < distance) {
                maxDistance = distance;
                outcast = e.getKey();
            }
        }

        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
