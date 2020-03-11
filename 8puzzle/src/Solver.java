import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static class Node implements Comparable<Node> {
        private final Board cur;
        private final Node pre;
        private final int move;
        private final int manhattan;

        private Node(Board cur, Node pre, int move) {
            this.cur = cur;
            this.pre = pre;
            this.move = move;
            this.manhattan = cur.manhattan();
        }

        @Override
        public int compareTo(Node other) {
            return move + manhattan - other.move - other.manhattan;
        }
    }

    private final Stack<Board> solution;
    private final int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("the initial board is null");
        MinPQ<Node> fringe = new MinPQ<>();
        fringe.insert(new Node(initial, null, 0));
        fringe.insert(new Node(initial.twin(), null, 0));
        Node node = fringe.delMin();
        while (!node.cur.isGoal()) {
            for (Board neighbor : node.cur.neighbors()) {
                if (node.pre == null || !neighbor.equals(node.pre.cur))
                    fringe.insert(new Node(neighbor, node, node.move + 1));
            }
            node = fringe.delMin();
        }
        solution = new Stack<>();
        while (node.pre != null) {
            solution.push(node.cur);
            node = node.pre;
        }
        solution.push(node.cur);  // don't forget the initial board
        if (node.cur.equals(initial)) moves = solution.size() - 1;
        else moves = -1;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
//        int[][] tiles = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};
        int[][] tiles = {{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};
        Solver solver = new Solver(new Board(tiles));
        StdOut.println(solver.solution());
        StdOut.println(solver.moves());
    }
}