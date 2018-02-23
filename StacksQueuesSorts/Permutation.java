import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Permutation
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        int i = 0;
        for ( String s : q) {
            if (i >= k) break;
            StdOut.println(s);
            i++;
        }
    }
}