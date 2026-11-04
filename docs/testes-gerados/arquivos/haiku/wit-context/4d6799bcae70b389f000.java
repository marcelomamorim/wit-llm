/*
 * @test
 * @summary Verify that DelayedWorkQueue iterator next() throws NoSuchElementException when exhausted or empty, observed through the public getQueue().iterator() path
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testEmptyQueueIteratorNextThrows();
        testExhaustedIteratorNextThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueIteratorNextThrows() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            Iterator<Runnable> it = executor.getQueue().iterator();
            boolean thrown = false;
            try {
                it.next();
            } catch (NoSuchElementException expected) {
                thrown = true;
            }
            check(thrown, "next() on empty queue iterator must throw NoSuchElementException");
        } finally {
            executor.shutdownNow();
        }
    }

    static void testExhaustedIteratorNextThrows() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.schedule(() -> { }, 1, TimeUnit.HOURS);
            Iterator<Runnable> it = executor.getQueue().iterator();
            Runnable first = it.next();
            check(first != null, "first next() must return the scheduled task");
            boolean thrown = false;
            try {
                it.next();
            } catch (NoSuchElementException expected) {
                thrown = true;
            }
            check(thrown, "next() after exhaustion must throw NoSuchElementException");
        } finally {
            executor.shutdownNow();
        }
    }
}
