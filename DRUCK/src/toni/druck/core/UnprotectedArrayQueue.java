package toni.druck.core;

/**
 * 
 * @author Thomas Nill
 * 
 *         angepasster Code aus Java. ArrayQue die NICHT thread sicher ist. Um
 *         Zeit für die beiden Queues in DataFIFO zu sparen.
 * 
 */
public class UnprotectedArrayQueue<E> {

    /** The queued items */
    private final E[] items;
    /** items index for next take, poll or remove */
    private int takeIndex;
    /** items index for next put, offer, or add. */
    private int putIndex;
    /** Number of items in the queue */
    private int count;

    public UnprotectedArrayQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = (E[]) new Object[capacity];
    }

    final private int inc(int i) {
        return (++i == items.length) ? 0 : i;
    }

    public void insert(E x) {
        items[putIndex] = x;
        putIndex = inc(putIndex);
        ++count;
    }

    public E extract() {
        final E[] items = this.items;
        E x = items[takeIndex];
        items[takeIndex] = null;
        takeIndex = inc(takeIndex);
        --count;
        return x;
    }

    public E peek() {
        return (count == 0) ? null : items[takeIndex];
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return (count == 0);
    }

}
