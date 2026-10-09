/* @test    @summary Test ForkJoinPool.submit(Runnable) method for null and normal Runnable inputs    @run main ForkJoinPoolWitupTest_P68845ed8cbcf */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P68845ed8cbcf {

    public static void main(String[] args) {
        testSubmitWithNull();
        testSubmitWithRunnable();
    }

    static void testSubmitWithNull() {
        ForkJoinPool pool = new ForkJoinPool();
        boolean exceptionThrown = false;
        try {
            pool.submit((Runnable) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when submitting null Runnable");
        }
    }

    static void testSubmitWithRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        final boolean[] executed = {false};
        Runnable task = () -> executed[0] = true;
        ForkJoinTask<?> submittedTask = pool.submit(task);
        submittedTask.join();
        if (!executed[0]) {
            throw new AssertionError("Runnable task was not executed");
        }
    }
}
