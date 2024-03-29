package uk.markausten;

import java.util.Iterator;

public class NonBlockingQueue<Item> implements Iterable<Item>
{
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue

    // helper linked list class
    private static class Node<Item>
    {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty queue.
     */
    public NonBlockingQueue()
    {
        first = null;
        last = null;
        n = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return {@code true} if this queue is empty; {@code false} otherwise
     */
    public boolean isEmpty()
    {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size()
    {
        return n;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     */
    public Item peek()
    {
        Item item = null;

        if (!isEmpty())
        {
            item = first.item;
        }

        return item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    public void put(Item item)
    {
        Node<Item> oldlast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;

        if (isEmpty())
        {
            first = last;
        }
        else
        {
            oldlast.next = last;
        }

        n++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     */
    public Item get()
    {
        Item item = null;

        if (!isEmpty())
        {

            item = first.item;
            first = first.next;
            n--;

            if (isEmpty())
            {
                last = null;   // to avoid loitering
            }
        }

        return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for (Item item : this)
        {
            s.append(item);
            s.append(' ');
        }

        return s.toString();
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()
    {
        return new ListIterator(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item>
    {
        private Node<Item> current;

        public ListIterator(Node<Item> first)
        {
            current = first;
        }

        public boolean hasNext()
        {
            return current != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            Item item = null;

            if (hasNext())
            {
                item = current.item;
                current = current.next;
            }

            return item;
        }
    }
}
