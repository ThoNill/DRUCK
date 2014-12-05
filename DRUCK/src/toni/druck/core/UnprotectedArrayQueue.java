package toni.druck.core;


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

	/**
	 * Inserts element at current put position, advances, and signals. Call only
	 * when holding lock.
	 */
	public void insert(E x) {
		items[putIndex] = x;
		putIndex = inc(putIndex);
		++count;
	}

	/**
	 * Extracts element at current take position, advances, and signals. Call
	 * only when holding lock.
	 */
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
