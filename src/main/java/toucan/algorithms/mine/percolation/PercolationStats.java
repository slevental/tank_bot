package toucan.algorithms.mine.percolation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PercolationStats {

    public static final Random RANDOM = new Random();
    private final int size;
    private final double[] res;

    /**
     * perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int n, int t) {
        size = n;
        res = new double[t];
        for (int i = 0; i < t; i++) {
            res[i] = experiment();
        }
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        double sum = 0;
        for (double each : res) {
            sum += each;
        }
        return sum / res.length;
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        double sum = 0;
        double mean = mean();
        for (double each : res) {
            sum += Math.pow(each - mean, 2);
        }
        return sum / (res.length - 1);
    }

    /**
     * returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(res.length);
    }

    /**
     * returns upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(res.length);
    }

    private double experiment() {
        Percolation p = new Percolation(size);
        int c = 0;
        List<Integer> rand = new ArrayList<Integer>(size * size);
        for (int i = 0; i < size * size; i++){
            rand.add(i);
        }
        Collections.shuffle(rand, RANDOM);
        while (!p.percolates()) {
            int next = rand.get(c++);
            int i = next % size;
            int j = next / size;
            if (!p.isOpen(i, j)){
                p.open(i, j);
            }
        }
        return (double) c / (size * size);
    }

    /**
     * test client, described below
     */
    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("Wrong argument size, expected 2");
        int size = Integer.parseInt(args[0]);
        int numberOfExperiments = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(size, numberOfExperiments);
        System.out.println("mean                    = " + round(stats.mean()));
        System.out.println("stdenv                  = " + round(stats.stddev()));
        System.out.println("95% confidence interval = " + round(stats.confidenceLo()) + ", " + round(stats.confidenceHi()));
    }

    private static double round(double num) {
        return Math.round(num * 100000) / 100000d;
    }
}