package com.github.JordanGuinn.QueueDemo;

import com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadFactory;
import com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadType;
import com.github.JordanGuinn.QueueDemo.model.BoundedQueue;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * This is the main class of the application, responsible for verifying the behavior of the
 * BoundedQueue implementation to any interested parties.
 */
public class BoundedQueueValidation {
    private static final Supplier<Double> doubleSupplier = () -> new Random().nextDouble();

    private static final int threadTypeCount = 5;
    private static final int elementCountPerThread = 1000;
    private static final int queueCapacity = 300;

    public static void main(String[] args) {
        System.out.println("\n**********************************************************************");
        System.out.println("Hello, and welcome to my custom queue project!\n");
        System.out.print("The purpose of this project is to demonstrate the behavior of my thread-safe\nBoundedQueue implementation.  ");
        System.out.print("It was designed to handle multiple Producer and \nConsumer Threads operating on it concurrently, without fear of data inconsistency.\n");
        System.out.print("For this particular demonstration, we'll operate on a BoundedQueue instance \ncreated from these parameters:\n\n");

        System.out.println("Thread Count (Per Type): " + threadTypeCount);
        System.out.println("Element Count Per Thread: " + elementCountPerThread);
        System.out.println("Total Queue Capacity: " + queueCapacity);

        System.out.println("\nLet's begin!\n");

        CountDownLatch latch = new CountDownLatch(threadTypeCount * 2);

        BoundedQueue<Double> mainQueue = new BoundedQueue<>(queueCapacity);
        QueueThreadFactory threadFactory = new QueueThreadFactory<>(mainQueue, doubleSupplier);

        for (int i = 0; i < threadTypeCount; i++) {
            threadFactory.getThread(QueueThreadType.Producer, elementCountPerThread, latch).start();
            threadFactory.getThread(QueueThreadType.Consumer, elementCountPerThread, latch).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println("\nOne or more threads interrupted while manipulating queue.  Exiting early...");
            System.exit(1);
        }

        System.out.println("\nAll Producer & Consumer Threads have completed!  Queue size is " + mainQueue.size() + ".");

        System.exit(0);
    }
}
