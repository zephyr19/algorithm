import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new IllegalArgumentException();
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException();
        String outcast = null;
        int max = Integer.MIN_VALUE;
        for (String s1 : nouns) {
            int distance = 0;
            for (String s2 : nouns) {
                distance += wordNet.distance(s1, s2);
            }
            if (distance > max) {
                max = distance;
                outcast = s1;
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String s = outcast.outcast(new String[] {"apple", "pear", "peach", "banana", "lime", "lemon",
                                      "blueberry", "strawberry", "mango", "watermelon", "potato"});
        System.out.println(s);
//        for (int t = 2; t < args.length; t++) {
//            In in = new In(args[t]);
//            String[] nouns = in.readAllStrings();
//            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
//        }
    }
}