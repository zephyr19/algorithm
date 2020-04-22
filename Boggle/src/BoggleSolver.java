import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class BoggleSolver {
    private static class Node {
        String word;
        int score;
        char c;
        Node left, middle, right;

        public Node(char c) {
            this.c = c;
        }
    }

    private Node root;

    private int rows;
    private int cols;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        HashMap<Integer, Integer> score = new HashMap<>();
        score.put(3, 1);
        score.put(4, 1);
        score.put(5, 2);
        score.put(6, 3);
        score.put(7, 5);
        score.put(8, 11);
        for (String word : dictionary) {
            int len = word.length();
            if (len > 2) {
                int s = len > 7 ? 11 : score.get(len);
                root = put(root, word, s, 0);
            }
        }
    }

    private Node put(Node node, String word, int score, int d) {
        if (node == null) {
            Node n = new Node(word.charAt(d));
            if (d != word.length() - 1) n.middle = put(n.middle, word, score, d + 1);
            else {
                n.word = word;
                n.score = score;
            }
            return n;
        }
        char c = node.c;
        if      (word.charAt(d) < c) node.left = put(node.left, word, score, d);
        else if (word.charAt(d) > c) node.right = put(node.right, word, score, d);
        else {
            if (d == word.length() - 1) {
                node.word = word;
                node.score = score;
            } else {
                node.middle = put(node.middle, word, score, d + 1);
            }
        }
        return node;
    }

    private Iterable<Integer> getNeighbor(int i, boolean[] marked) {
        ArrayList<Integer> neighbor = new ArrayList<>();
        int col = i % cols, row = i / cols;
        if (col != cols - 1) {
            if (!marked[i + 1]) neighbor.add(i + 1);
            if (row != 0) {
                if (!marked[i - cols]) neighbor.add(i - cols);
                if (!marked[i - cols + 1]) neighbor.add(i - cols + 1);
            }
            if (row != rows - 1) {
                if (!marked[i + cols]) neighbor.add(i + cols);
                if (!marked[i + cols + 1]) neighbor.add(i + cols + 1);
            }
        }
        if (col != 0) {
            if (!marked[i - 1]) neighbor.add(i - 1);
            if (row != 0) {
                if (col == cols - 1 && !marked[i - cols]) neighbor.add(i - cols);
                if (!marked[i - cols - 1]) neighbor.add(i - cols - 1);
            }
            if (row != rows - 1) {
                if (col == cols - 1 && !marked[i + cols]) neighbor.add(i + cols);
                if (!marked[i + cols - 1]) neighbor.add(i + cols - 1);
            }
        }
        if (cols == 1) {
            if (row != 0) {
                if (!marked[i - cols]) neighbor.add(i - cols);
            }
            if (row != rows - 1) {
                if (!marked[i + cols]) neighbor.add(i + cols);
            }
        }
        return neighbor;
    }


    private Node getNodeOf(Node node, char c) {
        if (node == null) return null;
        if (node.c > c)   return getNodeOf(node.left, c);
        if (node.c < c)   return getNodeOf(node.right, c);
        else              return node;
    }

    private void getWord(Node node, HashSet<String> words, BoggleBoard board, int i, boolean[] marked) {
        if (node == null) return;
        char c = board.getLetter(i / cols, i % cols);
        if      (c < node.c) getWord(node.left, words, board, i, marked);
        else if (c > node.c) getWord(node.right, words, board, i, marked);
        else {
            if (c == 'Q') node = getNodeOf(node.middle, 'U');
            if (node == null) return;
            if (node.word != null) words.add(node.word);
            boolean[] visited = Arrays.copyOf(marked, marked.length);
            visited[i] = true;
            for (int neighbor : getNeighbor(i, marked)) getWord(node.middle, words, board, neighbor, visited);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> words = new HashSet<>();
        rows = board.rows();
        cols = board.cols();
        boolean[] marked = new boolean[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                getWord(root, words, board, i * cols + j, marked);
            }
        }
        return words;
    }

    private int getScore(Node node, String word, int d) {
        if      (node == null)            return 0;
        if      (word.charAt(d) < node.c) return getScore(node.left, word, d);
        else if (word.charAt(d) > node.c) return getScore(node.right, word, d);
        else if (d != word.length() - 1)  return getScore(node.middle, word, d + 1);
        else                              return node.score;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return getScore(root, word, 0);
    }

    public static void main(String[] args) {
        In in = new In("dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
//        BoggleBoard board = new BoggleBoard("board4x4.txt");
        BoggleBoard board = new BoggleBoard("board-dichlorodiphenyltrichloroethanes.txt");
        int score = 0;
        ArrayList<String> words = new ArrayList<>();
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            words.add(word);
        }
        StdOut.println("Score = " + score);
        StdOut.println(words.size());
    }

}