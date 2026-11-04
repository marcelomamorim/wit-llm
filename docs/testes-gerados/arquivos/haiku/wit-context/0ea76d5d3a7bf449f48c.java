/*
 * @test
 * @summary Verify ForkJoinPool.invoke rejects a null task with NullPointerException and still runs valid tasks
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolWitupTest {

    static class Doubler extends RecursiveTask<Integer> {
        private final int n;

        Doubler(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            return n * 2;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullTaskThrowsNPE() {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            ForkJoinTask<Integer> nullTask = null;
            boolean thrown = false;
            try {
                pool.invoke(nullTask);
            } catch (NullPointerException expected) {
                thrown = true;
            }
            check(thrown, "invoke(null) must throw NullPointerException");
        } finally {
            pool.shutdown();
        }
    }

    static void testValidTaskReturnsResult() {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            Integer result = pool.invoke(new Doubler(21));
            check(result != null && result == 42, "invoke must return the task result");
        } finally {
            pool.shutdown();
        }
    }

    static void testPoolUsableAfterNullTask() {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            ForkJoinTask<Integer> nullTask = null;
            try {
                pool.invoke(nullTask);
            } catch (NullPointerException expected) {
                // expected
            }
            Integer result = pool.invoke(new Doubler(5));
            check(result != null && result == 10,
                  "pool must remain usable after a rejected null task");
        } finally {
            pool.shutdown();
        }
    }

    public static void main(String[] args) {
        testNullTaskThrowsNPE();
        testValidTaskReturnsResult();
        testPoolUsableAfterNullTask();
    }
}
