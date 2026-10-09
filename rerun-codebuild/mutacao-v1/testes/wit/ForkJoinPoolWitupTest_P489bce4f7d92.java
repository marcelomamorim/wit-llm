/*
 * @test
 * @summary Test for ForkJoinPool.submit(Runnable) method behavior
 * @run main ForkJoinPoolWitupTest_P489bce4f7d92
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P489bce4f7d92 {

    public static void main(String[] args) {
        testSubmitWithNullTask();
        testSubmitWithValidTask();
    }

    private static void testSubmitWithNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.submit((Runnable) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testSubmitWithValidTask() {
        ForkJoinPool pool = new ForkJoinPool();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // no-op
            }
        };
        ForkJoinTask<?> submittedTask = pool.submit(task);
        if (submittedTask == null) {
            throw new AssertionError("submit() returned null");
        }
        // Optionally, verify that the task is in a state that can be invoked or joined
        try {
            submittedTask.invoke();
        } catch (Exception e) {
            throw new AssertionError("invoke() on submitted task failed: " + e);
        }
    }
}
