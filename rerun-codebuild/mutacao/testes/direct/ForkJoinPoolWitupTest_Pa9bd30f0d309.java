/*
 * @test
 * @summary Test ForkJoinPool.execute(Runnable) method for basic behavior
 * @run main ForkJoinPoolWitupTest_Pa9bd30f0d309
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_Pa9bd30f0d309 {

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
        pool.execute(r);
        pool.shutdown();
        try {
            pool.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignore
        }
        assert executed[0] : "Runnable was not executed";
    }

    static void testExecuteWithNull() {
        ForkJoinPool pool = new ForkJoinPool(1);
        boolean exceptionThrown = false;
        try {
            pool.execute((Runnable) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException was not thrown for null Runnable";
        pool.shutdown();
    }
}
