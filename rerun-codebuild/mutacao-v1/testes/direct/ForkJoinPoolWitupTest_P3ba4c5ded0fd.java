/* @test    @summary Test ForkJoinPool.execute(Runnable) method for correct behavior    @run main ForkJoinPoolWitupTest_P3ba4c5ded0fd */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P3ba4c5ded0fd {

    public static void main(String[] args) {
        testExecuteWithRunnable();
        testExecuteWithNull();
    }

    static void testExecuteWithRunnable() {
        ForkJoinPool pool = new ForkJoinPool(1);
        final boolean[] executed = {false};
        Runnable r = () -> {
            executed[0] = true;
        };
        try {
            pool.execute(r);
            // Wait briefly to allow task to run
            Thread.sleep(50);
            if (!executed[0]) {
                throw new AssertionError("Runnable was not executed");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
    }

    static void testExecuteWithNull() {
        ForkJoinPool pool = new ForkJoinPool(1);
        boolean exceptionThrown = false;
        try {
            pool.execute((Runnable) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } finally {
            pool.shutdown();
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null Runnable");
        }
    }
}
