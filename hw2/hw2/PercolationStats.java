package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    private Percolation p = null;
    private double[] data;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        data = new double[T];
        for (int t = 0; t < T; t++) {
            p = pf.make(N);
            while(!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            data[t] = p.numberOfOpenSites() / (N * N);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(data);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(data);
    }
    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(data.length);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(data.length);
    }
}
