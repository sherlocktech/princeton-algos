/**
 * ResizingArrayQueueOfString
 */
public class ResizingArrayQueueOfString {

    private String[] q;
    private int head;
    private int tail;
    private int capacity;

    public ResizingArrayQueueOfString() {
        capacity = 1;
        q = new String[capacity];
        head = 0;
        tail = 0;
    }

    public void enqueue(String item) {
        q[tail] = item;
        tail = (tail + 1) % capacity;
        if (tail == capacity)
            resize(capacity * 2);
    }

    public String dequeue() {
        String item = q[head++];
        if (isEmpty()) {
            head = 0;
            tail = 0;
        }
    }

    public boolean isEmpty() {
        return head == tail;
    }
}