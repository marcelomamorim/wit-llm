/*
 * @test    @summary Test ForkJoinPool.execute with a simple Runnable.
 * @run main ForkJoinPoolWitupTest_P6899a3a77e9a
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest_P6899a3a77e9a {

    public static void main(String[] args) {
        testExecuteWithRunnable();
    }

    public static void testExecuteWithRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        final boolean[] executed = {false};
        Runnable task = () -> {
            executed[0] = true;
        };
        pool.execute(task);
        pool.shutdown();
        try {
            pool.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Ignored for test
        }
        assert executed[0] : "Runnable task was not executed";
    }
}
