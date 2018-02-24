import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;
    private int first;
    private int last;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }
    
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        q[last++] = item;
        if (last == q.length) last = 0;
        n++;
        if (n == q.length) resize(q.length * 2);
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int r = random();
        Item item = q[r];
        q[r] = q[first];
        q[first++] = null;
        if (first == q.length) first = 0;
        n--;
        if (n > 0 && n == q.length / 4) resize(q.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int r = random();
        Item item = q[r];
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private final Item[] a;
        public ArrayIterator() {
            a = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                a[i] = q[(first + i) % q.length];
            }
            StdRandom.shuffle(a);
        }
        public boolean hasNext()  { return i < n;                              }
        public void remove()      { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[i];
            i++;
            return item;
        }
    }

    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        first = 0;
        last = n;
        q = temp;
    }

    private int random() {
        int r;
        if (last < first) {
            r = StdRandom.uniform(first, last + q.length);
            r = r % q.length;
        } else {
            r = StdRandom.uniform(first, last);
        }

        return r;
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String[] file = {"A", "B", "C", "D"};
        for (String s : file) {
            queue.enqueue(s);
        }

        StdOut.println("iterator: ");
        for (String s : queue) {
            StdOut.print(s + " ");
        }

        StdOut.println();
        StdOut.println("dequeue: ");
        while (!queue.isEmpty()) {
            StdOut.print(queue.dequeue() + " ");
        }
    }
}