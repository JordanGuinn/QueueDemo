package com.github.JordanGuinn.QueueDemo.concurrent;

import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;
import org.junit.After;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

/**
 * <code>QueueThreadFactoryTest</code> simply ensures that any given instance of
 * <code>QueueThreadFactory</code> will appropriately provide Consumer & Producer threads
 * as necessary, in addition to throwing particular exceptions under certain circumstances.
 */
public class QueueThreadFactoryTest {

    private static final Supplier<Integer> intSupplier = () -> new Random().nextInt();

    @After
    public void writeSuccessToConsole() {
        System.out.println("\nTest has been completed.");
        System.out.println("*********************************************************\n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullParamThrowsIllegalArgumentException() {
        System.out.println("Executing 'nullParamThrowsIllegalArgumentException' test...");

        BoundedQueue<Integer> mainQueue = new BoundedQueue<>(50);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, intSupplier);

        threadFactory.getThread(null);
    }

    @Test
    public void factoryProvidesProducerThreads() throws InterruptedException {
        System.out.println("Starting 'factoryProvidesProducerThreads' test...\n");

        CountDownLatch latch = new CountDownLatch(1);

        BoundedQueue<Integer> mainQueue = new BoundedQueue<>(50);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, intSupplier);

        Thread producerThread = threadFactory.getThread(QueueThreadType.Producer, 50, latch);
        producerThread.start();

        latch.await();

        assertTrue(mainQueue.size() == 50);
    }

    @Test
    public void factoryProvidesConsumerThreads() throws InterruptedException {
        System.out.println("Starting 'factoryProvidesConsumerThreads' test...\n");

        CountDownLatch latch = new CountDownLatch(2);

        BoundedQueue<Integer> mainQueue = new BoundedQueue<>(50);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, intSupplier);

        Thread producerThread = threadFactory.getThread(QueueThreadType.Producer, 50, latch);
        Thread consumerThread = threadFactory.getThread(QueueThreadType.Consumer, 25, latch);
        producerThread.start();
        consumerThread.start();

        latch.await();

        assertTrue(mainQueue.size() == 25);
    }
}