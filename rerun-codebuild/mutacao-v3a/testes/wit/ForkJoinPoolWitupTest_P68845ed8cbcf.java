/*
 * @test
 * @summary Tests the submit(Runnable task) method in ForkJoinPool.
 * @run main ForkJoinPoolWitupTest_P68845ed8cbcf
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P68845ed8cbcf {

    public static void main(String[] args) {
        testSubmitWithNullTaskThrowsException();
        testSubmitWithValidRunnableReturnsForkJoinTask();
    }

    private static void testSubmitWithNullTaskThrowsException() {
        ForkJoinPool pool = new ForkJoinPool();
        boolean exceptionThrown = false;
        try {
            pool.submit((Runnable) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when submitting null task");
        }
    }

    private static void testSubmitWithValidRunnableReturnsForkJoinTask() {
        ForkJoinPool pool = new ForkJoinPool();
        Runnable task = () -> {};
        ForkJoinTask<?> submittedTask = pool.submit(task);
        if (submittedTask == null) {
            throw new AssertionError("submit() should not return null for valid task");
        }
        if (!(submittedTask instanceof ForkJoinTask)) {
            throw new AssertionError("Returned object is not an instance of ForkJoinTask");
        }
    }
}
