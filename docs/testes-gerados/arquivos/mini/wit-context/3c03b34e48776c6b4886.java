/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.execute(Runnable) for null and normal cases
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testExecuteWithNullCommand();
        testExecuteWithValidCommand();
    }

    private static void testExecuteWithNullCommand() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.execute(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } finally {
            executor.shutdownNow();
        }
    }

    private static void testExecuteWithValidCommand() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        final boolean[] ran = new boolean[1];
        Runnable command = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        executor.execute(command);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Executor did not terminate in time");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }
        if (!ran[0]) {
            throw new RuntimeException("Runnable command was not executed");
        }
    }
}
