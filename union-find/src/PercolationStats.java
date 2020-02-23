import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double NUM = 1.96;
    private final double[] sites;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new double[trials];
        mean = 0.0;
        stddev = 0.0;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            sites[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == 0.0) {
            mean = StdStats.mean(sites);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == 0.0) {
            stddev = StdStats.stddev(sites);
        }
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - NUM * stddev() / Math.sqrt(sites.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + NUM * stddev() / Math.sqrt(sites.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " +
                percolationStats.confidenceHi() + "]");
    }

}
