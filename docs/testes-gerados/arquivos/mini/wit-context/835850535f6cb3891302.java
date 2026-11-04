/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.DelayedWorkQueue.Itr.remove() exception paths
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.RejectedExecutionHandler;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testRemoveIllegalStateException();
        testRemoveUnsupportedOperationException();
    }

    static void testRemoveIllegalStateException() {
        // Create ScheduledThreadPoolExecutor with 1 thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Get the DelayedWorkQueue and its iterator via schedule and internal queue
        // Since DelayedWorkQueue and its iterator are package-private and no public access,
        // we test the behavior indirectly by submitting a task and cancelling it to simulate
        // the iterator remove() throwing IllegalStateException when lastRet < 0.

        // The iterator remove() throws IllegalStateException if lastRet < 0
        // We simulate this by calling remove() without next() on iterator

        // Since DelayedWorkQueue.Itr is not accessible, we test the behavior by
        // creating a Runnable that calls remove() on iterator without next()
        // but this is not possible publicly, so we test the observable behavior:
        // calling remove() on iterator before next() throws IllegalStateException

        // We test this by creating a dummy iterator from the queue and calling remove()
        // but DelayedWorkQueue is not public, so we test the public ScheduledThreadPoolExecutor
        // does not expose this directly.

        // Therefore, we test the expected exception by creating a dummy iterator from
        // the queue returned by getQueue() and calling remove() before next()

        java.util.concurrent.BlockingQueue<Runnable> queue = executor.getQueue();
        java.util.Iterator<Runnable> it = queue.iterator();

        boolean threw = false;
        try {
            it.remove(); // should throw IllegalStateException because next() not called
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException not thrown by iterator.remove() before next()");
        }

        executor.shutdownNow();
    }

    static void testRemoveUnsupportedOperationException() {
        // The WIT expath indicates that if lastRet >= 0, remove() throws UnsupportedOperationException
        // This corresponds to the iterator remove() after next() called

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        java.util.concurrent.BlockingQueue<Runnable> queue = executor.getQueue();
        java.util.Iterator<Runnable> it = queue.iterator();

        // Add a dummy task to the queue to have an element
        Runnable dummy = new Runnable() {
            public void run() {}
        };

        executor.schedule(dummy, 10, TimeUnit.MILLISECONDS);

        // Wait briefly to ensure task is in queue
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        it = queue.iterator();

        if (!it.hasNext()) {
            // No element to test, skip
            executor.shutdownNow();
            return;
        }

        it.next(); // move to first element

        boolean threw = false;
        try {
            it.remove(); // expected to throw UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by iterator.remove() after next()");
        }

        executor.shutdownNow();
    }
}
