package com.github.JordanGuinn.QueueDemo.test.concurrent;

/**
 * <code>QueueThreadType</code> encapsulates the full collection of thread types
 * available for immediate use with a <code>BoundedQueue</code>.  It's certainly
 * acceptable to generate other types of <code>Thread</code>s with their own custom
 * <code>Runnable</code> implementations, but only these two readily available
 * for immediate execution.
 */
public enum QueueThreadType {
    Producer,
    Consumer
}
