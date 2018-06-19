package com.github.JordanGuinn.QueueDemo.concurrent;

import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * The <code>Producer</code> class allows for the addition of n elements to any
 * particular <code>BoundedQueue</code> instance by a single thread.
 *
 * @see com.github.JordanGuinn.QueueDemo.concurrent.Consumer
 */
public class Producer<T> extends QueueRunnable<T> {
    private final Supplier<T> supplier;

    /**
     * Constructs a new Producer for the queue specified, utilizing the default element count.
     * A custom <code>Supplier</code> is also necessary in order to generate the elements to be
     * added to the queue.
     *
     * @param queue     BoundedQueue instance to be added to
     * @param supplier  Mechanism by which instances of T are produced
     */
    public Producer(BoundedQueue<T> queue, Supplier<T> supplier) {
        super(queue);
        this.supplier = supplier;
    }

    /**
     * Create a new Producer instance responsible for adding n elements to the queue provided
     * (n being the elementCount argument specified).  Once more, the custom <code>Supplier</code>
     * ensures this Producer has a mechanism by which to actually generate the elements to add to the queue.
     *
     * @param queue         BoundedQueue instance to be added to
     * @param supplier      Mechanism by which instances of T are produced
     * @param elementCount  Total number of elements to be added
     */
    public Producer(BoundedQueue<T> queue, Supplier<T> supplier, int elementCount) {
        super(queue, elementCount);
        this.supplier = supplier;
    }

    /**
     * Create a new Producer instance responsible for adding n elements to the queue provided
     * (n being the elementCount argument specified).  Once more, the custom <code>Supplier</code>
     * ensures this Producer has a mechanism by which to actually generate the elements to add to the queue.
     *
     * @param queue         BoundedQueue instance to be added to
     * @param supplier      Mechanism by which instances of T are produced
     * @param elementCount  Total number of elements to be added
     * @param latch
     */
    public Producer(BoundedQueue<T> queue, Supplier<T> supplier, int elementCount, CountDownLatch latch) {
        super(queue, elementCount, latch);
        this.supplier = supplier;
    }

    /**
     * <code>run</code> initiates execution of this Producer for whichever <code>Thread</code>
     * is responsible for it.
     */
    @Override
    public void run() {
        informOfExecutionStart();
        addToQueue();
        informOfExecutionCompletion();
    }

    /**
     * Using the Supplier of type T provided, <code>addToQueue</code> retrieves and adds n
     * elements to the BoundedQueue specified during creation of this <code>Producer</code>
     * (n being the elementCount also supplied during <code>Producer</code> instantiation).
     */
    private void addToQueue() {
        for (int i = 0; i < elementCount; i++) {
            queue.enqueue(supplier.get());
        }
    }
}
