/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.offer(Runnable x)
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testOfferWithNullRunnable();
        testOfferWithValidRunnable();
    }

    private static void testOfferWithNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        boolean exceptionThrown = false;
        try {
            executor.offer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null Runnable");
        }
        executor.shutdown();
    }

    private static void testOfferWithValidRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        final AtomicBoolean ran = new AtomicBoolean(false);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ran.set(true);
            }
        };
        boolean result = executor.offer(r);
        if (!result) {
            throw new AssertionError("offer() returned false for valid Runnable");
        }
        // Wait briefly to allow task execution
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executor.shutdown();
        if (!ran.get()) {
            throw new AssertionError("Runnable did not run after offer()");
        }
    }
}
