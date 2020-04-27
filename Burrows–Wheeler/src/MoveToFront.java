import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static char[] createLookup() {
        char[] lookup = new char[256];
        for (char i = 0; i < lookup.length; i++) lookup[i] = i;
        return lookup;
    }

    private static char updateLookup(char[] lookup, char input) {
        char preChar = lookup[0];
        for (char i = 0; i < lookup.length; i++) {
            if (lookup[i] == input) {
                lookup[i] = preChar;
                lookup[0] = input;
                return i;
            }
            char curChar = lookup[i];
            lookup[i] = preChar;
            preChar = curChar;
        }
        throw new IllegalArgumentException();
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] lookup = createLookup();
        while (!BinaryStdIn.isEmpty()) {
            char input = BinaryStdIn.readChar();
            BinaryStdOut.write(updateLookup(lookup, input));
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] lookup = createLookup();
        while (!BinaryStdIn.isEmpty()) {
            char output = lookup[BinaryStdIn.readChar()];
            BinaryStdOut.write(output);
            updateLookup(lookup, output);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
    }

}