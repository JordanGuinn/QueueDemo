package com.github.JordanGuinn.queue.concurrent;

import com.github.JordanGuinn.queue.model.BoundedQueue;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Jordan on 6/19/18.
 */
public abstract class QueueRunnable<T> implements Runnable {
    public static final int DEFAULT_ELEMENT_COUNT = 1000;

    final int elementCount;
    final BoundedQueue<T> queue;

    private CountDownLatch latch = null;

    /**
     *
     * @param queue BoundedQueue instance to be removed from
     */
    QueueRunnable(BoundedQueue<T> queue) {
        this(queue, DEFAULT_ELEMENT_COUNT);
    }

    /**
     *
     * @param queue         BoundedQueue instance to be added to
     * @param elementCount  Total number of elements to be removed
     */
    QueueRunnable(BoundedQueue<T> queue, int elementCount) {
        this.queue = queue;
        this.elementCount = elementCount;
    }

    /**
     *
     * @param queue         BoundedQueue instance to be added to
     * @param elementCount  Total number of elements to be removed
     * @param latch
     */
    QueueRunnable(BoundedQueue<T> queue, int elementCount, CountDownLatch latch) {
        this(queue, elementCount);
        this.latch = latch;
    }

    void informOfExecutionStart() {
        System.out.println(Thread.currentThread().getName() + " has begun execution.  Queue size is " + queue.size() + ".");
    }

    void informOfExecutionCompletion() {
        if(latch != null) {
            latch.countDown();
        }

        System.out.println(Thread.currentThread().getName() + " has completed execution.  Queue size is " + queue.size() + ".");
    }
}
