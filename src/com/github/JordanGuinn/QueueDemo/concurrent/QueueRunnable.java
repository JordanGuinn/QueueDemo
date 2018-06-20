package com.github.JordanGuinn.QueueDemo.concurrent;

import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;

import java.util.concurrent.CountDownLatch;

/**
 * The <code>QueueRunnable</code> class is a <code>BoundedQueue</code>-specific abstraction
 * around <code>Runnable</code>, keeping track of operational element count and the queue to
 * be operated upon when initiated.  It also has the capability of informing other threads
 * of it's execution state where necessary.
 */
public abstract class QueueRunnable<T> implements Runnable {
    public static final int DEFAULT_ELEMENT_COUNT = 1000;

    final int elementCount;
    final BoundedQueue<T> queue;

    private CountDownLatch latch = null;

    /**
     * Construct a new default QueueRunnable associated with the BoundedQueue provided.
     *
     * @param queue BoundedQueue instance to be operated upon
     */
    QueueRunnable(BoundedQueue<T> queue) {
        this(queue, DEFAULT_ELEMENT_COUNT);
    }

    /**
     * Create a new QueueRunnable instance responsible for applying n (elementCount) elements
     * to the BoundedQueue provided.
     *
     * @param queue         BoundedQueue instance to be operated upon
     * @param elementCount  Total number of elements to act with
     */
    QueueRunnable(BoundedQueue<T> queue, int elementCount) {
        this.queue = queue;
        this.elementCount = elementCount;
    }

    /**
     * In addition to applying n (elementCount) elements to the queue provided, this newly created
     * QueueRunnable object can also help synchronize external threads depending on it with the
     * assistance of the CountDownLatch provided.
     *
     * @param queue         BoundedQueue instance to be operated upon
     * @param elementCount  Total number of elements to act with
     * @param latch         Latch capable of being decremented upon Runnable completion
     */
    QueueRunnable(BoundedQueue<T> queue, int elementCount, CountDownLatch latch) {
        this(queue, elementCount);
        this.latch = latch;
    }

    /**
     * Print a general message indicating the start of this Runnable's execution.
     */
    void informOfExecutionStart() {
        System.out.println(Thread.currentThread().getName() + " has begun execution.  Queue size is " + queue.size() + ".");
    }

    /**
     * Print a general message indicating the successful completion of this Runnable's execution, in addition
     * to decrementing this Runnable's CountDownLatch (if it exists).
     */
    void informOfExecutionCompletion() {
        if(latch != null) {
            latch.countDown();
        }

        System.out.println(Thread.currentThread().getName() + " has completed execution.  Queue size is " + queue.size() + ".");
    }
}
