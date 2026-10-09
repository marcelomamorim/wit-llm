/* @test    @summary Test ForkJoinPool.submit(Runnable) for correctness and exception handling    @run main ForkJoinPoolWitupTest_P489bce4f7d92 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P489bce4f7d92 {

    public static void main(String[] args) {
        testNullRunnable();
        testValidRunnable();
    }

    static void testNullRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.submit((Runnable) null);
            throw new AssertionError("Expected NullPointerException for null Runnable");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }

    static void testValidRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        final boolean[] flag = {false};
        Runnable task = () -> {
            flag[0] = true;
        };
        ForkJoinTask<?> submittedTask = pool.submit(task);
        submittedTask.join();
        if (!flag[0]) {
            throw new AssertionError("Runnable task did not execute as expected");
        }
        if (submittedTask == null) {
            throw new AssertionError("submit() returned null");
        }
    }
}
