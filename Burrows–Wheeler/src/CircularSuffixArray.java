import java.util.PriorityQueue;

public class CircularSuffixArray {
    private static class Node implements Comparable<Node> {
        private String c;
        private final int i;

        public Node(String c, int i) {
            this.c = c;
            this.i = i;
        }

        @Override
        public int compareTo(Node o) {
            return c.compareTo(o.c);  // the smaller the c is, the more priority it is.
        }
    }

    private int[] indices;
    private final int LEN = 4;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        int len = s.length();
        indices = new int[len];
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < len; i++) priorityQueue.add(new Node(s.charAt(i), i));
        sort(priorityQueue, 0, s, 0);
    }

    private void sort(PriorityQueue<Node> priorityQueue, int offset, String s, int index) {
        if (priorityQueue == null) throw new IllegalArgumentException();
        if (priorityQueue.size() == 1) indices[index] = priorityQueue.remove().i;
        if (offset == s.length()) {
            while (!priorityQueue.isEmpty()) {
                indices[index++] = priorityQueue.remove().i;
            }
        }
        offset++;
        while (!priorityQueue.isEmpty()) {
            PriorityQueue<Node> subList = new PriorityQueue<>();
            char pre = priorityQueue.peek().c;
            while (!priorityQueue.isEmpty() && priorityQueue.peek().c == pre) {
                Node node = priorityQueue.remove();
                node.c = s.charAt((node.i + offset) % s.length());
                subList.add(node);
            }
            int size = subList.size();
            sort(subList, offset, s, index);
            index += size;
        }
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
