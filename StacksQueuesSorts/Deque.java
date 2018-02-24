import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;

    private class Node {
        private final Item item;
        private Node next;
        private Node prev;
        public Node(Item item) {
            this.item = item;
        }
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else           oldFirst.prev = first;
        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node(item);
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else           oldLast.next = last;
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (first.next == last)
            first = last;
        else
            first = first.next;
        if (first != null) first.prev = null;
        n--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (last.prev == first)
            last = first;
        else 
            last = last.prev;
        if (last != null) last.next = null;
        n--;
        return item;
    }

    /**
     * Returns an iterator that iterates over the item in this queue from front to end.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()    { return current != null;                       }
        public void remove()        { throw new UnsupportedOperationException();    }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        String[] file = {"A", "B", "C", "D"};
        for (String s : file) {
            d.addFirst(s);
        }

        StdOut.println("iterator: ");
        for (String s : d) {
            StdOut.print(s + " ");
        }
        StdOut.println();
        StdOut.println("removeFirst: ");
        while (!d.isEmpty()) {
            StdOut.print(d.removeFirst() + " ");
        }
    }
}