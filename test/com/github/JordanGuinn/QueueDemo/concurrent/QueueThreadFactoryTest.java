package com.github.JordanGuinn.QueueDemo.concurrent;

import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * Created by Jordan on 6/18/18.
 */
public class QueueThreadFactoryTest {
    private static final Supplier<Integer> intSupplier = () -> new Random().nextInt();

    @Test(expected = IllegalArgumentException.class)
    public void nullParamThrowsIllegalArgumentException() {
        BoundedQueue<Integer> mainQueue = new BoundedQueue<>(50);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, intSupplier);

        threadFactory.getThread(null);
    }

    @Test
    public void factoryProvidesProducerThreads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        BoundedQueue<Integer> mainQueue = new BoundedQueue<>(50);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, intSupplier);

        Thread producerThread = threadFactory.getThread(QueueThreadType.Producer, 50, latch);
        producerThread.start();

        latch.await();
    }

    @Test
    public void factoryProvidesConsumerThreads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        BoundedQueue<Integer> mainQueue = new BoundedQueue<>(50);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, intSupplier);

        Thread producerThread = threadFactory.getThread(QueueThreadType.Producer, 50, latch);
        Thread consumerThread = threadFactory.getThread(QueueThreadType.Consumer, 25, latch);
        producerThread.start();
        consumerThread.start();

        latch.await();
    }
}