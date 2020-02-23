import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridLength;
    private final int bottom;
    private final int top;
    private boolean[] grid;
    private int openSites;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF antiBackWash;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Cannot have a grid which length is less or equal to 0.");
        }
        gridLength = n;
        grid = new boolean[n * n];
        openSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        antiBackWash = new WeightedQuickUnionUF(n * n + 1);
        top = n * n;
        bottom = top + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int site = getSite(row, col);
        if (!grid[site]) {
            grid[site] = true;
            openSites++;
            int[] neighbors = {site - gridLength, site + gridLength, site + 1, site - 1};
            if (site % gridLength == (gridLength - 1)) {
                neighbors[2] = site;
            } else if (site % gridLength == 0) {
                neighbors[3] = site;
            }
            for (int neighbor : neighbors) {
                try {
                    if (grid[neighbor]) {
                        uf.union(site, neighbor);
                        antiBackWash.union(site, neighbor);
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    if (site < gridLength) {
                        uf.union(site, top);
                        antiBackWash.union(site, top);
                    }
                    if (site >= gridLength * (gridLength - 1)) {
                        uf.union(site, bottom);
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[getSite(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int site = getSite(row, col);
        return antiBackWash.connected(site, top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    // validate input row and col
    private void validate(int row, int col) {
//        if (row < 0 || col < 0 || row >= gridLength || col >= gridLength) {
        if (row <= 0 || col <= 0 || row > gridLength || col > gridLength) {
            throw new IllegalArgumentException("Invalidate inputs for row and col.");
        }
    }

    // return the one dimensional index of (row, col) pair
    private int getSite(int row, int col) {
//        return row * gridLength + col;
        return (row - 1) * gridLength + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // naive test
//        Percolation percolation = new Percolation(2);
//        boolean x = percolation.isOpen(1, 1);
//        boolean y = percolation.isFull(1, 1);
//        percolation.open(1, 1);
//        percolation.open(1, 2);
//        boolean notPercolating = percolation.percolates();
//        percolation.open(2, 1);
//        boolean percolates = percolation.percolates();
    }
}
