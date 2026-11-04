/*
 * @test    @summary Test the next() method of ScheduledThreadPoolExecutor for correct behavior.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testNextReturnsExpectedRunnable();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsExpectedRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task1 = () -> {};
        Runnable task2 = () -> {};
        executor.execute(task1);
        executor.execute(task2);
        try {
            Runnable result1 = executor.next();
            Runnable result2 = executor.next();
            if (result1 == null || result2 == null) {
                throw new AssertionError("next() returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextReturnsExpectedRunnable", e);
        }
        executor.shutdown();
    }

    private static void testNextThrowsNoSuchElementException() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // No tasks added, cursor should be at initial position
        try {
            executor.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextThrowsNoSuchElementException", e);
        }
        executor.shutdown();
    }
}
