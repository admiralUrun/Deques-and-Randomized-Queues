/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

   private Item[] queue;
   private int tail;
   private int n;


    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        // construct an empty randomized queue
    }
    public boolean isEmpty() {
        return n == 0;
        // is the randomized queue empty?
    }
    public int size() {
        return n;
        // return the number of items on the randomized queue
    }
    public void enqueue(Item item) {
        if (item == null) {
            throw new  java.lang.IllegalArgumentException();
        }

        if (tail == queue.length) {
            ensureCapasity(true);
            queue[tail] = item;
        } else {
            queue[tail] = item;
        }
        n++;
        tail++;
        // add the item
    }
    public Item dequeue() {
        cantReternIfRandomizedQueueIsEmpty();
        ensureCapasity(false);
        int random = randomFromZeroTo(tail);
        Item item = queue[random];
        queue[random] = queue[tail - 1];
        tail--;
        n--;
        queue[tail] = null;
        // remove and return a random item
        return item;
    }
    public Item sample() {
        cantReternIfRandomizedQueueIsEmpty();
        if (n - 1 == 0) {
            return queue[0];
        }
        return queue[randomFromZeroTo(tail)];
        // return a random item (but do not remove it)
    }

    private class IteratorData {
        Item[] q;
        int iteratorTail;
        int iteratorCount;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            IteratorData data = getDataForIterator();
            @Override
            public boolean hasNext() { return data.iteratorCount > 0; }
            @Override
            public Item next() {
                if (!hasNext()) { throw new NoSuchElementException(); }
                return removeItemForRandomIterator();
            }
            @Override
            public void remove() { throw new java.lang.UnsupportedOperationException(); }
            private Item removeItemForRandomIterator() {
                int random = randomFromZeroTo(data.iteratorTail);
                Item item = data.q[random];
                data.q[random] = data.q[data.iteratorTail - 1];
                data.iteratorTail--;
                data.iteratorCount--;
                data.q[data.iteratorTail] = null;
                return item;
            } };
    }
    private IteratorData getDataForIterator() {
        IteratorData data = new IteratorData();
        data.q = copyQueue();
        data.iteratorTail = copyInt(tail);
        data.iteratorCount = copyInt(n);
        return data;
    }


    private void ensureCapasity(boolean shouldGrow) {
        if (shouldGrow) {
            resize(queue.length * 2);
        } else {
            if (n == queue.length / 4) {
                resize(queue.length / 2);
            }
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int indexCopy = 0;
        for (int i = 0; i < tail; i++) {
            if (queue[i] != null) {
                copy[indexCopy] = queue[i];
                indexCopy++;
            }
        }
        tail = n;
        queue = copy;
        // reSize
    }

    private void cantReternIfRandomizedQueueIsEmpty() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }
    private int randomFromZeroTo(int i) {
        int random = StdRandom.uniform(0, i);
        if (random >= 0 && random < i) {
            return random;
        } else {
            while (random >= 0 && random < i) {
                random = StdRandom.uniform(0, i);
            }
            return random;
        }
    }
    private Item[] copyQueue() {
        Item[] q = (Item[]) new Object[n];
        for (int i = 0; i < n; i++) {
                q[i] = queue[i];
        }
        return q;
    }
    private int copyInt(int copy) {
        return copy;
    }

    public static void main(String[] args) {


        }
}
