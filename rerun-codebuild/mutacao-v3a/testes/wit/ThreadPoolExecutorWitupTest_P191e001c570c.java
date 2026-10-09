/* @test    @summary Test for ThreadPoolExecutor.allowCoreThreadTimeOut with valid input */
/* @run main ThreadPoolExecutorWitupTest_P191e001c570c */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P191e001c570c {

    public static void main(String[] args) {
        testAllowCoreThreadTimeOutWithValidValue();
        testAllowCoreThreadTimeOutWithZero();
        testAllowCoreThreadTimeOutWithNegative();
    }

    private static void testAllowCoreThreadTimeOutWithValidValue() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.BlockingQueue<Runnable>() {
            public boolean add(Runnable r) { return false; }
            public boolean offer(Runnable r) { return false; }
            public Runnable poll() { return null; }
            public Runnable poll(long timeout, java.util.concurrent.TimeUnit unit) { return null; }
            public Runnable take() { return null; }
            public int remainingCapacity() { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c) { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c, int maxElements) { return 0; }
            public java.util.Iterator<Runnable> iterator() { return null; }
            public java.util.Spliterator<Runnable> spliterator() { return null; }
        });
        executor.allowCoreThreadTimeOut(true);
        // No exception expected
    }

    private static void testAllowCoreThreadTimeOutWithZero() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.BlockingQueue<Runnable>() {
            public boolean add(Runnable r) { return false; }
            public boolean offer(Runnable r) { return false; }
            public Runnable poll() { return null; }
            public Runnable poll(long timeout, java.util.concurrent.TimeUnit unit) { return null; }
            public Runnable take() { return null; }
            public int remainingCapacity() { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c) { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c, int maxElements) { return 0; }
            public java.util.Iterator<Runnable> iterator() { return null; }
            public java.util.Spliterator<Runnable> spliterator() { return null; }
        });
        try {
            executor.allowCoreThreadTimeOut(false);
            // No exception expected
        } catch (IllegalArgumentException e) {
            // Not expected
            e.printStackTrace();
        }
    }

    private static void testAllowCoreThreadTimeOutWithNegative() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.BlockingQueue<Runnable>() {
            public boolean add(Runnable r) { return false; }
            public boolean offer(Runnable r) { return false; }
            public Runnable poll() { return null; }
            public Runnable poll(long timeout, java.util.concurrent.TimeUnit unit) { return null; }
            public Runnable take() { return null; }
            public int remainingCapacity() { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c) { return 0; }
            public int drainTo(java.util.Collection<? super Runnable> c, int maxElements) { return 0; }
            public java.util.Iterator<Runnable> iterator() { return null; }
            public java.util.Spliterator<Runnable> spliterator() { return null; }
        });
        try {
            executor.allowCoreThreadTimeOut(true);
            // No exception expected
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
