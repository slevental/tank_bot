package toucan.algorithms.mine.trees;

/**
 */
public class SumArray {
    private final int[] array;
    private final int[] buffer;


    public SumArray(int n) {
        this.array = new int[n];
        this.buffer = new int[n];
    }

    public void add(int i, int y){
        if (i >= array.length || i < 0)
            throw new IndexOutOfBoundsException("Max index is " + array.length);
        array[i] += y;
    }

    public int partialSum(int n) {
        int sum = 0;
        for (int i = 0; i < n; i++){
            sum += array[i];
        }
        return sum;
    }
}
