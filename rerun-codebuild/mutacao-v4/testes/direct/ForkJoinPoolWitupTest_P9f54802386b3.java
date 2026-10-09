/* @test
   @summary Tests the execute(Runnable) method of ForkJoinPool.
   @run main ForkJoinPoolWitupTest_P9f54802386b3
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class ForkJoinPoolWitupTest_P9f54802386b3 {
    public static void main(String[] args) {
        testExecuteWithNull();
        testExecuteWithRunnable();
    }

    static void testExecuteWithNull() {
        ForkJoinPool pool = new ForkJoinPool();
        boolean exceptionThrown = false;
        try {
            pool.execute((Runnable) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null task");
        }
    }

    static void testExecuteWithRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        AtomicBoolean executed = new AtomicBoolean(false);
        Runnable task = () -> {
            executed.set(true);
        };
        pool.execute(task);
        // Wait for task to complete
        pool.shutdown();
        try {
            pool.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!executed.get()) {
            throw new AssertionError("Runnable task was not executed");
        }
    }
}
