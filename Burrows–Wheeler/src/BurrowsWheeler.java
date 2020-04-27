import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder text = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            text.append(BinaryStdIn.readChar());
        }
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(text.toString());
        int len = circularSuffixArray.length();
        for (int i = 0; i < len; i++) {
            if (circularSuffixArray.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < len; i++) {
            int index = circularSuffixArray.index(i);
            BinaryStdOut.write(text.charAt((index + len - 1) % len));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        ArrayList<Character> text = new ArrayList<>();
        int first = BinaryStdIn.readInt();
        while (!BinaryStdIn.isEmpty()) {
            text.add(BinaryStdIn.readChar());
        }
        char[] sorted = new char[text.size()];
        int[] cnt = new int[256];
        for (int i = 0; i < sorted.length; i++) cnt[text.get(i)]++;
        int index = 0;
        for (int i = 0; i < cnt.length; i++) {
            for (int j = 0; j < cnt[i]; j++) sorted[index++] = (char) i;
        }
        int[] next = new int[sorted.length];
        Arrays.fill(cnt, 0);
        for (int i = 0; i < sorted.length; i++) {
            char c = sorted[i];
            for (int j = cnt[c]; j < sorted.length; j++) {
                if (c == text.get(j)) {
                    next[i] = j;
                    cnt[c] = j + 1;
                    break;
                }
            }
        }
        for (int i = 0; i < sorted.length; i++) {
            BinaryStdOut.write(sorted[first]);
            first = next[first];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
    }

}