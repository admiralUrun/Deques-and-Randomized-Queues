/* *****************************************************************************
 *  Name: Andrii Yakovenko
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node before;
    }
    private class NodeTuple {
        Node node;
        int size;
    }

    private Node first;
    private Node last;
    private int n;

    public Deque() {
        first = new Node();
        last = first;
        // construct an empty deque
    }

    public boolean isEmpty() {
       return n == 0;
        // is the deque empty?
    }

    public int size() {
        return n;
        // return the number of items on the deque
    }

    public void addFirst(Item item) {
        addNullAgrument(item);
        if (isEmpty()) {
            first.item = item;
            first.before = null;
        } else if (size() == 1) {
            Node node = new Node();
            node.item = first.item;
            node.next = first.next;
            node.before = first;
            first.next = node;
            first.item = item;
            last = first.next;
        } else {
            Node node = new Node();
            node.item = first.item;
            node.next = first.next;
            node.before = first;
            first.next.before = node;
            first.next = node;
            first.item = item;
        }
        n++;
        // add the item to the front
    }
    public void addLast(Item item) {
        addNullAgrument(item);
        if (isEmpty()) {
            first.item = item;
            first.before = null;
        } else {
            Node node = new Node();
            node.item = item;
            node.before = last;
            last.next = node;
            last = node;
        }
        n++;
        // add the item to the end
    }
    public Item removeFirst() {
        removeNull();
        Item item = first.item;
        if (size() - 1 == 0) {
            first.item = null;
        } else if (size() == 2) {
            first.item = first.next.item;
            first.next = null;
            last = first;
        } else {
            first.item = first.next.item;
            first.next.next.before = first;
            first.next = first.next.next;
        }
        n--;
        // remove and return the item from the front
        return item;
    }
    public Item removeLast() {
        removeNull();
        Item item;
        if (n == 1) {
            item = first.item;
            first.item = null;
            last = first;
        } else if (size() == 2) {
            item = last.item;
            first.next = null;
            last = first;
        } else {
            item = last.item;
            last = last.before;
            last.next = null;
        }
        n--;
        return item;
        // remove and return the item from the end
    }
    public Iterator<Item> iterator() {
        return new  Iterator<Item>() {
            NodeTuple tuple = copyFirstNode();
            Node current = tuple.node;
            int i = 0;
            @Override
            public boolean hasNext() {
                return i < tuple.size;
            }
            @Override
            public Item next() {
                if (!hasNext()) { throw new java.util.NoSuchElementException(); }
                Item item = current.item;
                current = current.next;
                i++;
                return item;
            }
            @Override
            public void remove() { throw new java.lang.UnsupportedOperationException(); }
        };
    }

    private void addNullAgrument(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }
    private void removeNull() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }
    private NodeTuple copyFirstNode() {
        NodeTuple tuple = new NodeTuple();
        Node node = new Node();
        node.next = first.next;
        node.item = first.item;
        node.before = first.before;
        tuple.node = node;
        tuple.size = n;
        return tuple;
    }

    public static void main(String[] args) {
        // unit testing (optional)
    }
}