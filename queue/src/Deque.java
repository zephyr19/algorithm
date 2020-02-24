import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node pre;

        private Node() {
            this.item = null;
            this.next = this;
            this.pre = this;
        }

        private Node(Item item, Node pre, Node next) {
            this.item = item;
            this.next = next;
            this.pre = pre;
        }
    }

    private final Node sentinel;
    private int size;

    // construct an empty deque
    public Deque() {
        sentinel = new Node();
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add 'null' to the Deque!");
        }
        Node node = new Node(item, sentinel, sentinel.next);
        sentinel.next.pre = node;
        sentinel.next = node;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add 'null' to the Deque!");
        }
        Node node = new Node(item, sentinel.pre, sentinel);
        sentinel.pre.next = node;
        sentinel.pre = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove element from a empty Deque!");
        }
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove element from a empty Deque!");
        }
        Item item = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        size--;
        return item;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node cur = sentinel.next;

        @Override
        public boolean hasNext() {
            return cur != sentinel;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Can't return next item when there is no next item!");
            }
            Item item = cur.item;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove operation won't be provided.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
        deque.removeFirst();
        deque.addLast(3);
        deque.removeFirst();
        deque.isEmpty();
        deque.size();
        for (int i = 0; i < 9; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 3; i++) {
            deque.removeLast();
        }
        for (int i : deque) {
            StdOut.println(i);
        }
    }
}