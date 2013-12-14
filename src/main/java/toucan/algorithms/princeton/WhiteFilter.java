package toucan.algorithms.princeton;

/*************************************************************************
 *  Compilation:  javac org.eslion.WhiteFilter.java
 *  Execution:    java org.eslion.WhiteFilter whitelist.txt < input.txt
 *  Dependencies: org.eslion.SET org.eslion.In.java org.eslion.StdIn.java org.eslion.StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/tinyTale.txt
 *                http://algs4.cs.princeton.edu/35applications/list.txt
 * 
 *  Read in a whitelist of words from a file. Then read in a list of
 *  words from standard input and print out all those words that
 *  are in the first file.
 * 
 *  % more tinyTale.txt 
 *  it was the best of times it was the worst of times 
 *  it was the age of wisdom it was the age of foolishness 
 *  it was the epoch of belief it was the epoch of incredulity 
 *  it was the season of light it was the season of darkness 
 *  it was the spring of hope it was the winter of despair
 *
 *  % more list.txt 
 *  was it the of 
 * 
 *  % java org.eslion.WhiteFilter list.txt < tinyTale.txt
 *  it was the of it was the of
 *  it was the of it was the of
 *  it was the of it was the of
 *  it was the of it was the of
 *  it was the of it was the of
 *
 *************************************************************************/

public class WhiteFilter {  
    public static void main(String[] args) {
        SET<String> set = new SET<String>();

        // read in strings and add to set
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            String word = in.readString();
            set.add(word);
        }

        // read in string from standard input, printing out all exceptions
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (set.contains(word))
                StdOut.println(word);
        }
    }
}
