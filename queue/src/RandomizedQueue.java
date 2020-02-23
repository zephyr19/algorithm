import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL = 16;
    Item[] value;
    int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        value = (Item[]) new Object[INITIAL];
        size = 0;
    }

    private RandomizedQueue(RandomizedQueue<Item> source) {
        size = source.size;
        value = (Item[]) new Object[source.value.length];
        System.arraycopy(source.value, 0, value, 0, value.length);
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
        larger();
        if (item == null) {
            throw new IllegalArgumentException("Can't add 'null' to the Queue!");
        }
        int i = getIndexBy(false);
        value[i] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        shrink();
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove element from a empty Queue!");
        }
        int i = getIndexBy(true);
        Item item = value[i];
        value[i] = null;
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove element from a empty Queue!");
        }
        int i = getIndexBy(true);
        return value[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this);
    }

    // return a validate index for 'exit' situation
    private int getIndexBy(boolean exit) {
        int i;
        do {
            i = StdRandom.uniform(value.length);
        } while (exit == (value[i] == null));
        return i;
    }

    // double value array if 'size' is upper than threshold 1/2
    private void larger() {
        if (size > value.length / 2) {
            Item[] newValue = (Item[]) new Object[value.length * 2];
            System.arraycopy(value, 0, newValue, 0, value.length);
            value = newValue;
        }
    }

    // shrink value array if 'size' is lower than threshold 1/4
    private void shrink() {
        if (size < value.length / 4) {
            Item[] newValue = (Item[]) new Object[value.length / 2];
            int i = 0;
            for (Item item : value) {
                if (item != null) {
                    newValue[i++] = item;
                }
            }
            value = newValue;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }
        randomizedQueue.sample();
        for (int i = 0; i < 8; i++) {
            randomizedQueue.sample();
            randomizedQueue.dequeue();
        }
        randomizedQueue.size();
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
            if (!hasNext()) {
                throw new NoSuchElementException("Can't return next item when there is no next item!");
            }
            return randomizedQueue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove operation won't be provided.");
        }
    }
}
