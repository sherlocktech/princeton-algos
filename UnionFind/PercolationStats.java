import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * PercolationStats
 */
public class PercolationStats {
    private final int       n;
    private final double    mean;
    private final double    stddev;
    private final double    confidenceLo;
    private final double    confidenceHi;

    private final double SQRT_NUMTRIALS = Math.sqrt((double) numTrials);
    private final double CONFIDENCE_95  = 1.96;

     public PercolationStats(int n, int numTrials) {
         if (!(n >= 0 && numTrials > 0))
            throw new IllegalArgumentException("Arguments must be greater than 0.");
        // perform numTrials independent experiments on an n-by-n grid
        this.n = n;
        
        // perform trials storing outcomes to trial array
        double[] trial = new double[numTrials]; // num of sites open for trial corresponding to index
        double sites = n * n;
        for (int i = 0; i < numTrials; i++) {
            Percolation p = new Percolation(n);
            double sitesOpen = performTrial(p);
            trial[i] = sitesOpen / sites;
        }

        mean = StdStats.mean(trial);
        stddev = StdStats.stddev(trial);
        confidenceLo = mean - ((CONFIDENCE_95 * stddev) / SQRT_NUMTRIALS);
        confidenceHi = mean + ((CONFIDENCE_95 * stddev) / SQRT_NUMTRIALS);
    }

    public double mean() {
        // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return confidenceLo;
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return confidenceHi;
    }

    private double performTrial(Percolation p) {
        // open cells until system percolates
        while (!p.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            p.open(row, col);
        }
        return (double) p.numberOfOpenSites();
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int numTrials = StdIn.readInt();
        PercolationStats pStats = new PercolationStats(n, numTrials);     
        
        StdOut.println("mean" + "                    = " + pStats.mean());
        
        StdOut.println("stddev" + "                  = " + pStats.stddev());
        
        double lo = pStats.confidenceLo();
        double hi = pStats.confidenceHi();
        double[] interval = {lo, hi};
        StdOut.print("95% confidence interval" + " = [");
        for (int i = 0; i < interval.length; i++) {
            StdOut.print(interval[i]);
        }
        StdOut.print("]");
    }
}