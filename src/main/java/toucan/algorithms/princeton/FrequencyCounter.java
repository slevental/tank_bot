package toucan.algorithms.princeton;

/*************************************************************************
 *  Compilation:  javac org.eslion.FrequencyCounter.java
 *  Execution:    java org.eslion.FrequencyCounter L < input.txt
 *  Dependencies: org.eslion.ST.java org.eslion.StdIn.java org.eslion.StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tnyTale.txt
 *                http://algs4.cs.princeton.edu/31elementary/tale.txt
 *                http://algs4.cs.princeton.edu/31elementary/leipzig100K.txt
 *                http://algs4.cs.princeton.edu/31elementary/leipzig300K.txt
 *                http://algs4.cs.princeton.edu/31elementary/leipzig1M.txt
 *
 *  Read in a list of words from standard input and print out
 *  the most frequently occurring word.
 *
 *  % java org.eslion.FrequencyCounter 1 < tinyTale.txt
 *  it 10
 *
 *  % java org.eslion.FrequencyCounter 8 < tale.txt
 *  business 122
 *
 *  % java org.eslion.FrequencyCounter 10 < leipzig1M.txt
 *  government 24763
 *
 *
 *************************************************************************/

public class FrequencyCounter {

    public static void main(String[] args) {
        int distinct = 0, words = 0;
        int minlen = Integer.parseInt(args[0]);
        ST<String, Integer> st = new ST<String, Integer>();

        // compute frequency counts
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (key.length() < minlen) continue;
            words++;
            if (st.contains(key)) { st.put(key, st.get(key) + 1); }
            else                  { st.put(key, 1); distinct++; }
        }

        // find a key with the highest frequency count
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words  = " + words);
    }
}
