package com.github.JordanGuinn.QueueDemo.model;

import com.github.JordanGuinn.QueueDemo.concurrent.QueueRunnable;
import com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadFactory;
import com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadType;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

/**
 * <code>BoundedQueueTest</code> ensures that adding to and removing from any
 * particular BoundedQueue works as expected, in both single-threaded and
 * multi-threaded environments.
 */
public class BoundedQueueTest {

    private static final Supplier<String> stringSupplier = () -> new Random().toString();
    private static final Supplier<Boolean> boolSupplier = () -> new Random().nextBoolean();

    @After
    public void writeSuccessToConsole() {
        System.out.println();
        System.out.println("Test has been completed.");
        System.out.println("*********************************************************");
        System.out.println();
    }

    @Test
    public void enqueueAddsToQueue() {
        System.out.println("Starting 'enqueueAddsToQueue' test...");
        System.out.println();

        BoundedQueue<Boolean> mainQueue = new BoundedQueue<>(100);

        for (int i = 0; i < 100; i++) {
            mainQueue.enqueue(i % 2 == 0);
        }

        assertTrue(mainQueue.size() == 100);
    }

    @Test
    public void enqueueBlocksWhenFull() throws InterruptedException {
        System.out.println("Starting 'enqueueBlocksWhenFull' test...");
        System.out.println();

        CountDownLatch latch = new CountDownLatch(1);

        BoundedQueue<Boolean> queue = new BoundedQueue<>(100);
        QueueThreadFactory queueThreadFactory = new QueueThreadFactory<>(queue, boolSupplier);

        for (int i = 0; i < 100; i++) {
            queue.enqueue(true);
        }

        Thread enqueueThread = queueThreadFactory.getThread(QueueThreadType.Producer, 1, latch);
        enqueueThread.start();

        assertTrue(queue.size() == 100);

        queue.dequeue();
        latch.await();

        assertTrue(queue.size() == 100);
    }

    @Test
    public void enqueueQuietlyExitsWhenInterrupted() throws InterruptedException {
        System.out.println("Starting 'enqueueQuietlyExitsWhenInterrupted' test...");
        System.out.println();

        BoundedQueue<Boolean> queue = new BoundedQueue<>(100);
        QueueThreadFactory queueThreadFactory = new QueueThreadFactory<>(queue, boolSupplier);

        for (int i = 0; i < 100; i++) {
            queue.enqueue(true);
        }

        Thread enqueueThread = queueThreadFactory.getThread(QueueThreadType.Producer, 1);

        enqueueThread.start();

        enqueueThread.interrupt();
        enqueueThread.join();

        assertTrue(queue.size() == 100);
        assertTrue(enqueueThread.getState().equals(Thread.State.TERMINATED));
    }

    @Test
    public void dequeueRemovesFromQueue() {
        System.out.println("Starting 'dequeueRemovesFromQueue' test...");
        System.out.println();

        BoundedQueue<String> mainQueue = new BoundedQueue<>(100);

        for (int i = 0; i < 100; i++) {
            mainQueue.enqueue("String-Example-" + i);
        }

        for (int i = 0; i < 100; i++) {
            mainQueue.dequeue();
        }

        assertTrue(mainQueue.size() == 0);
    }

    @Test
    public void dequeueBlocksWhenEmpty() throws InterruptedException {
        System.out.println("Starting 'dequeueBlocksWhenEmpty' test...");
        System.out.println();

        BoundedQueue<String> queue = new BoundedQueue<>(100);
        QueueThreadFactory queueThreadFactory = new QueueThreadFactory<>(queue, stringSupplier);

        for (int i = 0; i < 100; i++) {
            queue.enqueue(new Random().toString());
        }

        for (int i = 0; i < 100; i++) {
            queue.dequeue();
        }

        assertTrue(queue.size() == 0);

        Thread dequeueThread = queueThreadFactory.getThread(QueueThreadType.Consumer, 1);
        dequeueThread.start();

        assertTrue(queue.size() == 0);

        queue.enqueue(new Random().toString());
        dequeueThread.join();

        assertTrue(queue.size() == 0);
        assertTrue(dequeueThread.getState().equals(Thread.State.TERMINATED));
    }

    ///////////////////////////////////////////////////////////////


    @Test
    public void smallQueueWithFewThreadsCompletes() throws InterruptedException {
        System.out.println("Starting 'smallQueueWithFewThreadsCompletes' test...");
        System.out.println();

        queueHandlesConcurrentOperations(5, 5, 5);
    }

    @Test
    public void smallQueueWithManyThreadsCompletes() throws InterruptedException {
        System.out.println("Starting 'smallQueueWithManyThreadsCompletes' test...");
        System.out.println();

        queueHandlesConcurrentOperations(5, 20, 20);
    }

    @Test
    public void mediumQueueWithFewThreadsCompletes() throws InterruptedException {
        System.out.println("Starting 'mediumQueueWithFewThreadsCompletes' test...");
        System.out.println();

        queueHandlesConcurrentOperations(50, 10, 10);
    }

    @Test
    public void mediumQueueWithManyThreadsCompletes() throws InterruptedException {
        System.out.println("Starting 'mediumQueueWithManyThreadsCompletes' test...");
        System.out.println();

        queueHandlesConcurrentOperations(50, 40, 40);
    }

    @Test
    public void largeQueueWithFewThreadsCompletes() throws InterruptedException {
        System.out.println("Starting 'largeQueueWithFewThreadsCompletes' test...");
        System.out.println();

        queueHandlesConcurrentOperations(500, 5, 5);
    }

    @Test
    public void largeQueueWithManyThreadsCompletes() throws InterruptedException {
        System.out.println("Starting 'largeQueueWithManyThreadsCompletes' test...");
        System.out.println();

        queueHandlesConcurrentOperations(1000, 75, 75);
    }

    ///////////////////////////////////////////////////////////////

    private void queueHandlesConcurrentOperations(int queueCapacity, int consumerThreadCount, int producerThreadCount) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(consumerThreadCount + producerThreadCount);

        Collection<Thread> producerThreads = new ArrayList<>();
        Collection<Thread> consumerThreads = new ArrayList<>();

        BoundedQueue<String> mainQueue = new BoundedQueue<>(queueCapacity);
        QueueThreadFactory queueThreadFactory = new QueueThreadFactory<>(mainQueue, stringSupplier);

        for (int i = 0; i < producerThreadCount; i++) {
            producerThreads.add(queueThreadFactory.getThread(QueueThreadType.Producer, QueueRunnable.DEFAULT_ELEMENT_COUNT, latch));
        }

        for (int i = 0; i < consumerThreadCount; i++) {
            consumerThreads.add(queueThreadFactory.getThread(QueueThreadType.Consumer, QueueRunnable.DEFAULT_ELEMENT_COUNT, latch));
        }

        producerThreads.forEach(Thread::start);
        consumerThreads.forEach(Thread::start);

        latch.await();

        assertTrue(mainQueue.size() == 0);
    }
}