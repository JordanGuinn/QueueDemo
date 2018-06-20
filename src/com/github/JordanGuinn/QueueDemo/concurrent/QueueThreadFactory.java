package com.github.JordanGuinn.QueueDemo.concurrent;

import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * The <code>QueueThreadFactory</code> class is responsible for generating the various types of immediately
 * executable <code>Thread</code>s for the <code>BoundedQueue</code> implementation.
 */
public class QueueThreadFactory<T> {
    private final BoundedQueue<T> queue;
    private final Supplier<T> supplier;

    private final ThreadGroup consumerThreadGroup = new ThreadGroup(QueueThreadType.Consumer.name());
    private int consumerThreadCount = 0;

    private final ThreadGroup producerThreadGroup = new ThreadGroup(QueueThreadType.Producer.name());
    private int producerThreadCount = 0;

    /**
     *
     * @param _queue
     * @param supplier
     */
    public QueueThreadFactory(BoundedQueue<T> _queue, Supplier<T> supplier) {
        this.queue = _queue;
        this.supplier = supplier;
    }

    /**
     * @param threadType    The type of thread to be created
     * @param elementCount  The total number of elements this newly generated queue thread will be responsible
     *                      for either producing or consuming
     *
     * @return
     */
    public Thread getThread(QueueThreadType threadType, int elementCount) throws IllegalArgumentException {
        return getThread(threadType, elementCount, null);
    }

    /**
     * @param threadType    The type of thread to be created
     * @param elementCount  The total number of elements this newly generated queue thread will be responsible
     *                      for either producing or consuming
     * @param latch
     *
     * @return
     */
    public Thread getThread(QueueThreadType threadType, int elementCount, CountDownLatch latch) throws IllegalArgumentException {
        if (threadType == null) {
            throw new IllegalArgumentException("QueueThreadType required to create Thread");
        }

        Thread thread;
        String threadName;

        switch (threadType) {
            case Consumer:
                threadName = QueueThreadType.Consumer.name() + "-" + consumerThreadCount;
                thread = new Thread(consumerThreadGroup, new Consumer<>(queue, elementCount, latch), threadName);

                consumerThreadCount++;
                break;
            case Producer:
                threadName = QueueThreadType.Producer.name() + "-" + producerThreadCount;
                thread = new Thread(producerThreadGroup, new Producer<>(queue, supplier, elementCount, latch), threadName);

                producerThreadCount++;
                break;
            default:
                throw new IllegalArgumentException("Invalid ThreadType provided");
        }

        return thread;
    }
}
