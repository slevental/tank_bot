package toucan.algorithms.princeton;

/*************************************************************************
 *  Compilation:  javac org.eslion.RandomSeq.java
 *  Execution:    java org.eslion.RandomSeq N lo hi
 *
 *  Prints N numbers between lo and hi.
 *
 *  % java org.eslion.RandomSeq 5 100.0 200.0
 *  123.43
 *  153.13
 *  144.38
 *  155.18
 *  104.02
 *
 *************************************************************************/

public class RandomSeq { 
    public static void main(String[] args) {

        // command-line arguments
        int N = Integer.parseInt(args[0]);

        if (args.length == 1) {
            // generate and print N numbers between 0.0 and 1.0
            for (int i = 0; i < N; i++) {
                double x = StdRandom.uniform();
                StdOut.println(x);
            }
        }

        else if (args.length == 3) {
            double lo = Double.parseDouble(args[1]);
            double hi = Double.parseDouble(args[2]);

            // generate and print N numbers between lo and hi
            for (int i = 0; i < N; i++) {
                double x = StdRandom.uniform(lo, hi);
                StdOut.printf("%.2f\n", x);
            }
        }

        else {
            throw new RuntimeException("Invalid number of arguments");
        }
    }
}
