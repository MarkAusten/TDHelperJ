package uk.markausten;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T>
{
    private Queue<T> queue = new LinkedList<>();
    private Condition isFullCondition;
    private Condition isEmptyCondition;
    private Lock lock;
    private int limit;

    public BlockingQueue()
    {
        this(Integer.MAX_VALUE);
    }

    public BlockingQueue(int limit)
    {
        this.limit = limit;
        lock = new ReentrantLock();
        isFullCondition = lock.newCondition();
        isEmptyCondition = lock.newCondition();
    }

    public void put(T item)
    {
        lock.lock();

        try
        {
            while (isFull())
            {
                try
                {
                    isFullCondition.await();
                }
                catch (InterruptedException ex)
                {
                }
            }

            queue.add(item);
            isEmptyCondition.signalAll();
        }
        finally
        {
            lock.unlock();
        }
    }

    public T get()
    {
        T item;

        lock.lock();

        try
        {
            while (isEmpty())
            {
                try
                {
                    isEmptyCondition.await();
                }
                catch (InterruptedException ex)
                {
                }
            }

            item = queue.poll();
            isFullCondition.signalAll();
        }
        finally
        {
            lock.unlock();
        }

        return item;
    }

    public boolean isEmpty()
    {
        return queue.size() == 0;
    }

    public boolean isFull()
    {
        return queue.size() == limit;
    }
}
