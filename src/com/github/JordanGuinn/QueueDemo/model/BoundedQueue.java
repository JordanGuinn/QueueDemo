package com.github.JordanGuinn.QueueDemo.model;

import java.util.ArrayList;

/**
 * <code>BoundedQueue</code> is a thread-safe, blocking implementation of
 * <code>ProducerConsumerQueue</code>, complete with a specifiable upper limit.
 * It was designed to handle use by multiple <code>Producer</code> and
 * <code>Consumer</code> threads concurrently, without concern of deadlock, livelock
 * or read/write collisions.
 */
public class BoundedQueue<T> implements ProducerConsumerQueue<T> {
    private final int queueCapacity;
    private final ArrayList<T> queue = new ArrayList<>();

    /**
     * Initialize an empty queue with the upper limit capped at the <code>queueCapacity</code>
     * argument provided.
     *
     * @param queueCapacity The total number of elements that can be held within this queue.
     */
    public BoundedQueue(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    /**
     * Add the element provided to the beginning of the queue.  If the queue is already at capacity,
     * block the calling thread until an element is removed from the queue.  It should be noted that
     * while blocking, the calling thread relinquishes the lock of this BoundedQueue so that any other
     * dequeue threads may come along and actually remove an element.
     *
     * When complete, notify any and all threads waiting for the intrinsic lock of this BoundedQueue.
     *
     * @param item Element to be added to the beginning of the queue.
     */
    @Override
    public synchronized void enqueue(T item) {
        try {
            while (queue.size() >= queueCapacity) {
                wait();
            }
        } catch (InterruptedException e) {
            //  Instead of catching this, consider propagating this Exception up the chain?
            Thread.currentThread().interrupt();
            return;
        }

        queue.add(0, item);
        notifyAll();
    }

    /**
     * Remove the very last element in the queue.  If the queue is already empty, block the
     * calling thread until an element is added to the queue.  Similar to <code>enqueue</code>,
     * the calling thread relieves ownership of this BoundedQueue lock while blocking so that
     * any other enqueue threads may come along and actually add an element.
     *
     * When complete, notify any and all threads waiting for the intrinsic lock of this BoundedQueue.
     *
     * @return Element freshly removed from the end of the queue.
     */
    @Override
    public synchronized T dequeue() {
        try {
            while (queue.isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {
            //  Instead of catching this, consider propagating this Exception up the chain?
            Thread.currentThread().interrupt();
            return null;
        }

        T item = queue.remove(queue.size() - 1);
        notifyAll();

        return item;
    }

    /**
     *
     * Calculate the total number of elements contained within the queue at any given moment.
     *
     * @return  The total number of elements within the queue at this exact moment
     */
    public int size() {
        return queue.size();
    }
}
