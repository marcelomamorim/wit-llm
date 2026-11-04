/* @test    @summary Test isQueued method of AbstractQueuedSynchronizer for thread presence detection    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testIsQueuedWithThreadInQueue();
        testIsQueuedWithThreadNotInQueue();
    }

    private static void testIsQueuedWithThreadInQueue() throws InterruptedException {
        final Object lock = new Object();
        Thread threadInQueue = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        threadInQueue.start();
        synchronized (lock) {
            lock.notifyAll();
        }
        Thread.sleep(50); // Ensure thread is waiting
        AbstractQueuedSynchronizer aqs = new AbstractQueuedSynchronizer() {
            @Override
            protected boolean tryAcquire(int arg) { return true; }
            @Override
            protected boolean tryRelease(int arg) { return true; }
        };
        // Use reflection to set tail to simulate queue with thread
        try {
            java.lang.reflect.Field tailField = AbstractQueuedSynchronizer.class.getDeclaredField("tail");
            tailField.setAccessible(true);
            Object node = new Object() {
                Thread thread = threadInQueue;
                Object prev;
            };
            tailField.set(aqs, node);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        boolean result = aqs.isQueued(threadInQueue);
        System.out.println("isQueued with thread in queue: " + result);
        assert result : "Thread should be detected as queued";
    }

    private static void testIsQueuedWithThreadNotInQueue() {
        AbstractQueuedSynchronizer aqs = new AbstractQueuedSynchronizer() {
            @Override
            protected boolean tryAcquire(int arg) { return true; }
            @Override
            protected boolean tryRelease(int arg) { return true; }
        };
        Thread threadNotInQueue = new Thread();
        boolean result = aqs.isQueued(threadNotInQueue);
        System.out.println("isQueued with thread not in queue: " + result);
        assert !result : "Thread should not be detected as queued";
    }
}
