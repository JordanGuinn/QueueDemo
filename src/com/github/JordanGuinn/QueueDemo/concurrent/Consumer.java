package com.github.JordanGuinn.QueueDemo.concurrent;

import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;

import java.util.concurrent.CountDownLatch;

/**
 * The <code>Consumer</code> class allows for the removal of n elements from any
 * particular <code>BoundedQueue</code> instance by a single thread.
 *
 * @see com.github.JordanGuinn.QueueDemo.concurrent.Producer
 */
public class Consumer<T> extends QueueRunnable<T> {

    /**
     * Generate a new Consumer instance responsible for pulling elements from the
     * provided BoundedQueue.
     *
     * @param queue BoundedQueue instance to be removed from
     */
    public Consumer(BoundedQueue<T> queue) {
        super(queue);
    }

    /**
     * Create a new Consumer capable of pulling n elements from the provided BoundedQueue,
     * with n being the elementCount argument.
     *
     * @param queue         BoundedQueue instance to be added to
     * @param elementCount  Total number of elements to be removed
     */
    public Consumer(BoundedQueue<T> queue, int elementCount) {
        super(queue, elementCount);
    }

    /**
     * Create a new Consumer instance which, in addition to maintaining responsibility for
     * removing n (elementCount) elements from the queue provided, is also capable of
     * informing other threads of it's completion with the specified CountDownLatch.
     *
     * @param queue         BoundedQueue instance to be added to
     * @param elementCount  Total number of elements to be removed
     * @param latch         Latch to be decremented upon completion of this Runnable
     */
    public Consumer(BoundedQueue<T> queue, int elementCount, CountDownLatch latch) {
        super(queue, elementCount, latch);
    }

    /**
     * <code>run</code> initiates execution of this Producer for whichever <code>Thread</code>
     * is responsible for it.
     */
    @Override
    public void run() {
        informOfExecutionStart();
        removeFromQueue();
        informOfExecutionCompletion();
    }

    /**
     * <code>removeFromQueue</code> removes n elements from the BoundedQueue specified during creation of this
     * <code>Consumer</code>, with n being the elementCount also supplied during <code>Consumer</code> creation.
     */
    private void removeFromQueue() {
        for (int i = 0; i < elementCount; i++) {
            queue.dequeue();
        }
    }
}