///////////////////////////////////////////////////////////////////////
// Copyright (C) Bin Yu
// All rights reserved
///////////////////////////////////////////////////////////////////////

import edu.princeton.cs.algs4.StdRandom;      
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats { 
    private double[] threshold;
    public PercolationStats(int n, int trials) { 
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0) throw new IllegalArgumentException("n is out of bounds");
        if (trials <= 0) throw new IllegalArgumentException("trials is out of bounds");
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
            }
            threshold[i] = (perc.numberOfOpenSites()+0.0)/(n*n);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        double ans = StdStats.mean(threshold);
        return ans;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold 
        double ans = StdStats.stddev(threshold);
        return ans;
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - (1.96*stddev())/Math.sqrt(threshold.length);
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval 
        return mean() + (1.96*stddev())/Math.sqrt(threshold.length);
    }
    public static void main(String[] args) {
        // test client (described below)
//        int n = Integer.parseInt(args[0]);
//        int trials = Integer.parseInt(args[1]);
//        PercolationStats percstats = new PercolationStats(n, trials);
//        System.out.println("mean = "+ percstats.mean());
//        System.out.println("stddev = "+ percstats.stddev());
//        System.out.println("95% confidence interval = ["+ percstats.confidenceLo()+", "+ percstats.confidenceHi()+"]");        
    }
} 