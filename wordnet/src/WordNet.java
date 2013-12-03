import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private final SAP sap;
    private final Map<String, SET<Integer>> nounToId = new HashMap<String, SET<Integer>>();
    private final Map<Integer, String> idToSynset = new HashMap<Integer, String>();

    public WordNet(String synsets, String hypernyms) {
        int size = processSynSets(synsets);
        Digraph digraph = new Digraph(size);
        processHypernyms(hypernyms, digraph);

        if (!isRootedDAG(digraph)) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(digraph);
    }

    private boolean isRootedDAG(Digraph digraph) {
        RootedDAG rootedDAG = new RootedDAG(digraph);
        return rootedDAG.isRootedDAG();
    }

    private void processHypernyms(String hypernyms, Digraph digraph) {
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            Integer synsetId = Integer.valueOf(fields[0]);

            for (int i = 1; i < fields.length; i++) {
                Integer hypernymId = Integer.valueOf(fields[i]);
                digraph.addEdge(synsetId, hypernymId);
            }
        }
    }

    private int processSynSets(String synsets) {
        int i = 0;
        In in = new In(synsets);
        while (in.hasNextLine()) {
            i++;
            String line = in.readLine();
            String[] fields = line.split(",");
            Integer id = Integer.valueOf(fields[0]);

            idToSynset.put(id, fields[1]);

            for (String n : fields[1].split(" ")) {
                SET<Integer> ids = nounToId.get(n);

                if (ids == null) {
                    ids = new SET<Integer>();
                    nounToId.put(n, ids);
                }

                ids.add(id);
            }
        }

        return i;
    }

    public Iterable<String> nouns() {
        return nounToId.keySet();
    }

    public boolean isNoun(String word) {
        return nounToId.keySet().contains(word);
    }

    public int distance(String nounA, String nounB) {
        SET<Integer> aIds = nounToId.get(nounA);
        SET<Integer> bIds = nounToId.get(nounB);

        validate(aIds, bIds);

        return sap.length(aIds, bIds);
    }

    public String sap(String nounA, String nounB) {
        SET<Integer> aIds = nounToId.get(nounA);
        SET<Integer> bIds = nounToId.get(nounB);

        validate(aIds, bIds);

        int ancestor = sap.ancestor(aIds, bIds);
        return idToSynset.get(ancestor);
    }

    private void validate(SET<Integer> aIds, SET<Integer> bIds) {
        if (aIds == null || bIds == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

    }
}
