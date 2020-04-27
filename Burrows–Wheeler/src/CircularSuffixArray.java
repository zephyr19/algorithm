import java.util.Arrays;

public class CircularSuffixArray {
    private Integer[] indices;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        int len = s.length();
        indices = new Integer[len];
        for (int i = 0; i < len; i++) indices[i] = i;
        Arrays.sort(indices, (o1, o2) -> {
            for (int i = 0; i < len; i++) {
                int cmp = s.charAt((o1 + i) % len) - s.charAt((o2 + i) % len);
                if      (cmp < 0) return -1;
                else if (cmp > 0) return +1;
            }
            return 0;
        });
    }

    // length of s
    public int length() {
        return indices.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= indices.length) throw new IllegalArgumentException();
        return indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        assert circularSuffixArray.index(0) == 11;
        assert circularSuffixArray.length() == 12;
        assert circularSuffixArray.index(11) == 2;
    }

}
