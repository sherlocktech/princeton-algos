public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }
    
    public void enqueue(Item item) {

    }

    public Item dequeue() {

    }                   // remove and return a random item

    public Item sample() {

    }                    // return a random item (but do not remove it)

    public Iterator<Item> iterator() {

    }        // return an independent iterator over items in random order
    
    public static void main(String[] args) {

    }  // unit testing (optional)
}