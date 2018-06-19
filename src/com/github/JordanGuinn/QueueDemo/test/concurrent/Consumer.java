package com.github.JordanGuinn.QueueDemo.test.concurrent;

import com.github.JordanGuinn.QueueDemo.test.model.BoundedQueue;

import java.util.concurrent.CountDownLatch;

/**
 * The <code>Consumer</code> class allows for the removal of n elements from any
 * particular <code>BoundedQueue</code> instance by a single thread.
 *
 * @see com.github.JordanGuinn.QueueDemo.test.concurrent.Producer
 */
public class Consumer<T> extends QueueRunnable<T> {
    static final int DEFAULT_ELEMENT_COUNT = 1000;

    /**
     *
     * @param queue BoundedQueue instance to be removed from
     */
    public Consumer(BoundedQueue<T> queue) {
        super(queue);
    }

    /**
     *
     * @param queue         BoundedQueue instance to be added to
     * @param elementCount  Total number of elements to be removed
     */
    public Consumer(BoundedQueue<T> queue, int elementCount) {
        super(queue, elementCount);
    }

    /**
     *
     * @param queue         BoundedQueue instance to be added to
     * @param elementCount  Total number of elements to be removed
     * @param latch
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