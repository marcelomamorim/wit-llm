/*
 * @test
 * @summary Tests for ForkJoinPool.invoke method behavior with null task
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testInvokeWithNullTask();
        testInvokeWithValidTask();
    }

    public static void testInvokeWithNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke((ForkJoinTask<?>) null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testInvokeWithValidTask() {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> task = new ForkJoinTask<Integer>() {
            @Override
            public Integer getRawResult() {
                return 42;
            }

            @Override
            protected void setRawResult(Integer value) {
                // no-op
            }

            @Override
            protected boolean exec() {
                return true;
            }
        };
        Integer result = pool.invoke(task);
        if (result == null || result != 42) {
            System.err.println("Unexpected result from invoke: " + result);
            System.exit(1);
        }
    }
}
