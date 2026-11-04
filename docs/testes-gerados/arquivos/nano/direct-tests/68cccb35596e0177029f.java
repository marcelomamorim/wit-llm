/*
 * @test    @summary Test add method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testAddReturnsTrueForRunnable();
        testAddWithNullRunnable();
    }

    public static void testAddReturnsTrueForRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> {};
        boolean result = executor.add(task);
        assert result : "add should return true for a valid Runnable";
        executor.shutdown();
    }

    public static void testAddWithNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.add(null);
            assert false : "add should throw NullPointerException for null";
        } catch (NullPointerException expected) {
            // expected
        } finally {
            executor.shutdown();
        }
    }
}
