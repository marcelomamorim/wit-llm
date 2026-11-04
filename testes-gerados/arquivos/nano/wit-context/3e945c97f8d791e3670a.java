/* @test    @summary Test for ThreadPoolExecutor.shutdownNow() method behavior and exception handling    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.List;
import java.util.concurrent.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownNowReturnsList();
        testShutdownNowLocksAndUnlocks();
    }

    private static void testShutdownNowReturnsList() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.BlockingQueue<Runnable>() {
            public boolean add(Runnable e) { return false; }
            public boolean offer(Runnable e) { return false; }
            public Runnable remove() { return null; }
            public Runnable poll() { return null; }
            public Runnable element() { return null; }
            public Runnable peek() { return null; }
            public void put(Runnable e) throws InterruptedException {}
            public boolean offer(Runnable e, long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException { return false; }
            public Runnable take() throws InterruptedException { return null; }
            public int remainingCapacity() { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c) { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c, int maxElements) { return 0; }
        });
        List<Runnable> tasks = executor.shutdownNow();
        if (tasks == null) {
            throw new AssertionError("shutdownNow() returned null");
        }
    }

    private static void testShutdownNowLocksAndUnlocks() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.BlockingQueue<Runnable>() {
            public boolean add(Runnable e) { return false; }
            public boolean offer(Runnable e) { return false; }
            public Runnable remove() { return null; }
            public Runnable poll() { return null; }
            public Runnable element() { return null; }
            public Runnable peek() { return null; }
            public void put(Runnable e) throws InterruptedException {}
            public boolean offer(Runnable e, long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException { return false; }
            public Runnable take() throws InterruptedException { return null; }
            public int remainingCapacity() { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c) { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c, int maxElements) { return 0; }
        });
        try {
            executor.shutdownNow();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        // Verify that mainLock is unlocked after shutdownNow
        ReentrantLock lock = new ReentrantLock();
        try {
            executor.mainLock.lock();
            // lock acquired
        } finally {
            executor.mainLock.unlock();
        }
    }
}
