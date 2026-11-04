/*
 * @test
 * @summary Test submit(Runnable) method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSubmitRunnableReturnsFuture();
        testSubmitRunnableExecutesTask();
        testSubmitNullTaskThrows();
    }

    static void testSubmitRunnableReturnsFuture() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> {};
        Future<?> future = executor.submit(task);
        assert future != null : "Future should not be null";
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new AssertionError("Task execution interrupted or failed", e);
        }
        executor.shutdown();
    }

    static void testSubmitRunnableExecutesTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        final boolean[] executed = {false};
        Runnable task = () -> executed[0] = true;
        Future<?> future = executor.submit(task);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new AssertionError("Task execution interrupted or failed", e);
        }
        assert executed[0] : "Runnable task should have been executed";
        executor.shutdown();
    }

    static void testSubmitNullTaskThrows() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.submit((Runnable) null);
            throw new AssertionError("Submitting null task should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            executor.shutdown();
        }
    }
}
