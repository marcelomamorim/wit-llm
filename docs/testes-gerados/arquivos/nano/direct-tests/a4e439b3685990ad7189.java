/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.isEmpty() method for correctness.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testIsEmptyOnNewExecutor();
        testIsEmptyAfterShutdown();
        testIsEmptyAfterShutdownNow();
        testIsEmptyWithActiveTasks();
    }

    static void testIsEmptyOnNewExecutor() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        boolean result = executor.isEmpty();
        if (!result) {
            throw new AssertionError("Expected executor to be empty upon creation.");
        }
        executor.shutdown();
    }

    static void testIsEmptyAfterShutdown() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.shutdown();
        boolean result = executor.isEmpty();
        if (!result) {
            throw new AssertionError("Expected executor to be empty after shutdown.");
        }
    }

    static void testIsEmptyAfterShutdownNow() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.shutdownNow();
        boolean result = executor.isEmpty();
        if (!result) {
            throw new AssertionError("Expected executor to be empty after shutdownNow.");
        }
    }

    static void testIsEmptyWithActiveTasks() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10); // Allow task to start
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        boolean result = executor.isEmpty();
        if (result) {
            throw new AssertionError("Expected executor not to be empty when tasks are active.");
        }
        executor.shutdown();
    }
}
