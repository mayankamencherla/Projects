import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats 
{
  private Percolation percolation;
  private int T;
  private double[] threshold;                // the value of threshold p* in an array for N tries
  private int OpenSites;                        // number of the open sites according to the experiment
  
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
     if(N==0 || T ==0)
     {
       throw new IllegalArgumentException("Value is out of range");
     }
     
     threshold = new double[T];              // threshold is now a 1 by T array for every try.
     this.T = T;
     
     int randx,randy;                        // random numbers for percolation i and j;
     
     for(int i=0; i<T; i++)
     {
       percolation = new Percolation(N);     // calling the constructor
       randx = StdRandom.uniform(1,N+1);     // returns a number in [1,N+1);
       randy = StdRandom.uniform(1,N+1);
       percolation.open(randx,randy);
       OpenSites = 1;
       
       while(!percolation.percolates())
       {
         randx = StdRandom.uniform(1,N+1);     // returns a number in [1,N+1);
         randy = StdRandom.uniform(1,N+1);
         
         if(!percolation.isOpen(randx,randy))
         {
           percolation.open(randx,randy);
           OpenSites++;
         }
       }
     
       threshold[i] = ((double)OpenSites)/(N*N);
     }
   }
   
   public double mean()                      // sample mean of percolation threshold
   {
     return StdStats.mean(threshold);
   }
   
   public double stddev()                    // sample standard deviation of percolation threshold
   {
     return StdStats.stddev(threshold);
   }
   
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
     return mean() - (1.96*stddev())/Math.sqrt(T);
   }
   
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
     return mean() + (1.96*stddev())/Math.sqrt(T);
   }

   public static void main(String[] args)    // test client (described below)
   {
     int T,N;
     
     T = Integer.parseInt(args[0]);
     N = Integer.parseInt(args[1]);
     
     PercolationStats percStat = new PercolationStats(T,N);
     
     System.out.println("Percolation Stats for T=" + T + " and N=" + N);
     System.out.println("Mean is " + percStat.mean());
     System.out.println("Standard Deviation is " + percStat.stddev());
     System.out.println("confidence Low is " + percStat.confidenceLo());
     System.out.println("confidence High is " + percStat.confidenceHi());
   }
}