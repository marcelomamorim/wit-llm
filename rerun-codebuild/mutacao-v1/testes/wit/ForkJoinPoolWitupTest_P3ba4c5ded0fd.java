/* @test    @summary Test ForkJoinPool.execute(Runnable) for null input and normal execution    @run main ForkJoinPoolWitupTest_P3ba4c5ded0fd */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P3ba4c5ded0fd {

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
            throw new AssertionError("Expected NullPointerException when executing null Runnable");
        }
    }

    static void testExecuteWithRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        final boolean[] executed = {false};
        Runnable task = () -> {
            executed[0] = true;
        };
        pool.execute(task);
        // Wait for task to complete
        pool.shutdown();
        try {
            if (!pool.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                throw new AssertionError("Pool did not terminate in time");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!executed[0]) {
            throw new AssertionError("Runnable task was not executed");
        }
    }
}
