package com.github.JordanGuinn.QueueDemo.model;

import java.util.ArrayList;

/**
 * Created by Jordan on 6/15/18.
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
     * @param item Object to be added to the beginning of the queue.
     */
    @Override
    public synchronized void enqueue(T item) {
        try {
            while (queue.size() >= queueCapacity) {
                wait();
            }
        } catch (InterruptedException e) {
            //  Instead of catching this, consider propagating this Exception up the chain
            Thread.currentThread().interrupt();
            return;
        }

        queue.add(0, item);
        notifyAll();
    }

    /**
     * @return Object freshly removed from the end of the queue.
     */
    @Override
    public synchronized T dequeue() {
        try {
            while (queue.isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {
            //  Instead of catching this, consider propagating this Exception up the chain
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
