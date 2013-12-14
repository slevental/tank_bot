package toucan.algorithms.princeton;

/*************************************************************************
 *  Compilation:  javac org.eslion.DegreesOfSeparation.java
 *  Execution:    java org.eslion.DegreesOfSeparation filename delimiter source
 *  Dependencies: org.eslion.SymbolGraph.java org.eslion.Graph.java org.eslion.BreadthFirstPaths.java org.eslion.StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/routes.txt
 *                http://algs4.cs.princeton.edu/41undirected/movies.txt
 *  
 *  
 *  %  java org.eslion.DegreesOfSeparation routes.txt " " "JFK"
 *  LAS
 *     JFK
 *     ORD
 *     DEN
 *     LAS
 *  DFW
 *     JFK
 *     ORD
 *     DFW
 *  EWR
 *     Not in database.
 *
 *  % java org.eslion.DegreesOfSeparation movies.txt "/" "Bacon, Kevin"
 *  Kidman, Nicole
 *     Bacon, Kevin
 *     Woodsman, The (2004)
 *     Grier, David Alan
 *     Bewitched (2005)
 *     Kidman, Nicole
 *  Grant, Cary
 *     Bacon, Kevin
 *     Planes, Trains & Automobiles (1987)
 *     Martin, Steve (I)
 *     Dead Men Don't Wear Plaid (1982)
 *     Grant, Cary
 *
 *  % java org.eslion.DegreesOfSeparation movies.txt "/" "Animal House (1978)"
 *  Titanic (1997)
 *     Animal House (1978)
 *     Allen, Karen (I)
 *     Raiders of the Lost Ark (1981)
 *     Taylor, Rocky (I)
 *     Titanic (1997)
 *  To Catch a Thief (1955)
 *     Animal House (1978)
 *     Vernon, John (I)
 *     Topaz (1969)
 *     Hitchcock, Alfred (I)
 *     To Catch a Thief (1955)
 *
 *************************************************************************/

public class DegreesOfSeparation {
    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        String source    = args[2];

        // org.eslion.StdOut.println("Source: " + source);

        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        Graph G = sg.G();
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }

        int s = sg.index(source);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        while (!StdIn.isEmpty()) {
            String sink = StdIn.readLine();
            if (sg.contains(sink)) {
                int t = sg.index(sink);
                if (bfs.hasPathTo(t)) {
                    for (int v : bfs.pathTo(t)) {
                        StdOut.println("   " + sg.name(v));
                    }
                }
                else {
                    StdOut.println("Not connected");
                }
            }
            else {
                StdOut.println("   Not in database.");
            }
        }
    }
}
