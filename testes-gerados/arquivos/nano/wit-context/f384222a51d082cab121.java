/*
 * @test
 * @summary Tests the submit(Runnable) method of ForkJoinPool for null input.
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testSubmitNullTask();
        testSubmitValidTask();
    }

    private static void testSubmitNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.submit((Runnable) null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static void testSubmitValidTask() {
        ForkJoinPool pool = new ForkJoinPool();
        final boolean[] executed = {false};
        Runnable task = () -> executed[0] = true;
        ForkJoinTask<?> submittedTask = pool.submit(task);
        try {
            submittedTask.get();
            if (!executed[0]) {
                System.err.println("Task was not executed");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Exception during task execution: " + e);
            System.exit(1);
        }
    }
}
