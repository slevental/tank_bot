package toucan.algorithms.mine.graphs;

/**
 */
public class NodeUtils {

    public static Node wrap(final int index){
        return new Node() {
            public int getIndex() {
                return index;
            }

            public Node getNext() {
                return null;
            }
        };
    }
}
