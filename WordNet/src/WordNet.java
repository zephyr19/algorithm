import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {

    private final ArrayList<String>[] synset;
    private final HashMap<String, ArrayList<Integer>> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        In syn = new In(synsets);
        nouns = new HashMap<>();
        int index = 0;
        while (!syn.isEmpty()) {
            String[] strings = syn.readLine().split(",");
            index = Integer.parseInt(strings[0]);
            for (String noun : strings[1].split(" ")) {
                if (nouns.containsKey(noun)) {
                    nouns.get(noun).add(index);
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(index);
                    nouns.put(noun, arrayList);
                }
            }
        }
        synset = (ArrayList<String>[]) new ArrayList[index + 1];
        for (int i = 0; i < synset.length; i++) {
            synset[i] = new ArrayList<>();
        }
        for (String noun : nouns.keySet()) {
            ArrayList<Integer> indices = nouns.get(noun);
            for (int i : indices) {
                synset[i].add(noun);
            }
        }
        Digraph digraph = new Digraph(synset.length);
        In hyp = new In(hypernyms);
        while (!hyp.isEmpty()) {
            String[] strings = hyp.readLine().split(",");
            int ancestor = Integer.parseInt(strings[0]);
            for (int i = 1; i < strings.length; i++) {
                digraph.addEdge(ancestor, Integer.parseInt(strings[i]));
            }
        }
        Topological topological = new Topological(digraph);
        if (!topological.hasOrder()) throw new IllegalArgumentException("The digragh is not DAG");
        int root = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0) root++;
            if (root > 1) throw new IllegalArgumentException("The digragh contains multi roots");
        }
        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        ArrayList<String> arrayLists = synset[sap.ancestor(nouns.get(nounA), nouns.get(nounB))];
        return String.join(" ", arrayLists.toArray(new String[0]));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wordNet.distance("jimdandy", "thing"));
        System.out.println(wordNet.sap("backbiter", "RU_486"));
        System.out.println(wordNet.nouns.get("abstract_entity"));
        System.out.println(wordNet.nouns.get("abstraction"));
    }
}