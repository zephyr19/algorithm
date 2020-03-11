import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(tiles.length).append("\n");
        for (int[] tile : tiles) {
            for (int entry : tile) {
                stringBuffer.append(' ').append(entry);
            }
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int pivot = 1;
        int hammingNum = 0;
        for (int[] tile : tiles) {
            for (int entry : tile) {
                if (entry != pivot && entry != 0) hammingNum++;
                pivot++;
            }
        }
        return hammingNum;
    }

    private int manhattanDistance(int m, int n) {
        return Math.abs(m % dimension() - n % dimension()) + Math.abs(m / dimension() - n / dimension());
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int pivot = 1;
        int manhattanNum = 0;
        for (int[] tile : tiles) {
            for (int entry : tile) {
                if (entry != pivot && entry != 0) manhattanNum += manhattanDistance(entry - 1, pivot - 1);
                pivot++;
            }
        }
        return manhattanNum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;  // y can't be null
        else if (y == this) return true;  // same thing
        else if (!y.getClass().equals(this.getClass())) return false;  // check if 'y' is an instance of Board
        Board other = (Board) y;  // cast to same type
        return Arrays.deepEquals(tiles, other.tiles);  // System deepEquals
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        final int blank = getBlank();
        final int i = blank / dimension();
        final int j = blank % dimension();
        if (i > 0) neighbors.add(new Board(swap(i, j, i - 1, j)));
        if (j > 0) neighbors.add(new Board(swap(i, j, i, j - 1)));
        if (i < dimension() - 1) neighbors.add(new Board(swap(i, j, i + 1, j)));
        if (j < dimension() - 1) neighbors.add(new Board(swap(i, j, i, j + 1)));
        return neighbors;
    }

    // get the position of '0'
    private int getBlank() {
        int blankPosition = 0;
        for (int[] tile : tiles) {
            for (int entry : tile) {
                if (entry == 0) return blankPosition;
                blankPosition++;
            }
        }
        throw new IllegalArgumentException("The board is illegal");
    }

    private int[][] deepCopy(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        // create a deep copy of tiles
        for (int k = 0; k < tiles.length; k++) {
            System.arraycopy(tiles[k], 0, copy[k], 0, tiles.length);
        }
        return copy;
    }

    private int[][] swap(int i, int j, int m, int n) {
        int[][] copy = deepCopy(tiles);
        int temp = copy[i][j];
        copy[i][j] = copy[m][n];
        copy[m][n] = temp;
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] != 0 && tiles[0][1] != 0) return new Board(swap(0, 0, 0, 1));
        else return new Board(swap(1, 0, 1, 1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
//        int[][] tiles = {{1, 0}, {2, 3}};
        Board board = new Board(tiles);
        StdOut.println(board);
        StdOut.println(board.twin());
        for (Board neighbor : board.neighbors()) StdOut.println(neighbor);
        StdOut.println(board.equals(board.twin()));
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
    }
}