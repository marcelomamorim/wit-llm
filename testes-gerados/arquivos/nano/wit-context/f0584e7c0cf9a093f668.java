/*
 * @test
 * @summary Tests for ForkJoinPool.execute(Runnable)
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testExecuteWithNullTask();
        testExecuteWithRegularRunnable();
    }

    public static void testExecuteWithNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        boolean exceptionThrown = false;
        try {
            pool.execute((Runnable) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when executing null task");
        }
    }

    public static void testExecuteWithRegularRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        final boolean[] executed = {false};
        Runnable r = new Runnable() {
            @Override
            public void run() {
                executed[0] = true;
            }
        };
        pool.execute(r);
        // Wait briefly to allow task execution
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!executed[0]) {
            throw new AssertionError("Runnable task was not executed");
        }
    }
}
