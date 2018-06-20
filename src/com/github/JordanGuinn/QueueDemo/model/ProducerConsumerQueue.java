package com.github.JordanGuinn.QueueDemo.model;

/**
 * <code>ProducerConsumerQueue</code> is a pretty standard queue interface, exposing mechanisms
 * for both adding and removing elements as necessary.  The utilization of generics also makes it
 * easy to allow use with just about any data type.
 *
 * TODO: Consider throwing InterruptedExceptions directly, as opposed to handling and suppressing them.
 */
public interface ProducerConsumerQueue<T> {
    /**
     * The <code>enqueue</code> method simply takes an item of type <code>T</code> and appends it
     * to the end of the queue.
     *
     * @param item  The item to be added to the queue.
     */
    void enqueue(T item);

    /**
     * The <code>dequeue</code> method removes and returns the first item contained within the queue.
     *
     * @return  The item removed from the queue.
     */
    T dequeue();
}
