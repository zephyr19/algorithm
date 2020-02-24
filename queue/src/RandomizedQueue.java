import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] value;
    private int size;

    // construct an empty randomized queue
//    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
       value = (Item[]) new Object[1];
       size = 0;
    }

//    @SuppressWarnings("unchecked")
    private RandomizedQueue(RandomizedQueue<Item> source) {
        value = (Item[]) new Object[source.value.length];
        System.arraycopy(source.value, 0, value, 0, value.length);
        size = source.size;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add 'null' to the Queue!");
        if (size == value.length) resize(value.length * 2);
        value[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Can't remove element from a empty Queue!");
        if (size < value.length / 4) resize(value.length / 2);
        int i = StdRandom.uniform(size--);
        Item item = value[i];
        value[i] = value[size];
        value[size] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Can't remove element from a empty Queue!");
        return value[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this);
    }

    // resize the array in a new size
//    @SuppressWarnings("unchecked")
    private void resize(int length) {
        Item[] newVal = (Item[]) new Object[length];
        System.arraycopy(value, 0, newVal, 0, size);
        value = newVal;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(4);
        randomizedQueue.sample();
        randomizedQueue.dequeue();
        randomizedQueue.isEmpty();
        randomizedQueue.size();
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }
        for (Integer i : randomizedQueue) {
            StdOut.println(i);
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        RandomizedQueue<Item> randomizedQueue;

        private RandomizedQueueIterator(RandomizedQueue<Item> source) {
            randomizedQueue = new RandomizedQueue<>(source);
        }

        @Override
        public boolean hasNext() {
            return !randomizedQueue.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Can't return next item when there is no next item!");
            return randomizedQueue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove operation won't be provided.");
        }
    }
}