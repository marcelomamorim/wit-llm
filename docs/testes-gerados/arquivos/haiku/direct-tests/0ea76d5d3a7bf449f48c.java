/*
 * @test
 * @summary Basic behavior of ForkJoinPool.invoke: returns task result, rejects null, propagates task failure
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class ConstTask extends RecursiveTask<Integer> {
        private final int value;
        ConstTask(int value) { this.value = value; }
        @Override
        protected Integer compute() { return value; }
    }

    static class FailingTask extends RecursiveTask<Integer> {
        @Override
        protected Integer compute() {
            throw new IllegalStateException("boom");
        }
    }

    static void testReturnsResult(ForkJoinPool pool) {
        Integer r = pool.invoke(new ConstTask(42));
        check(r != null && r == 42, "expected 42 but got " + r);
    }

    static void testRepeatedInvocations(ForkJoinPool pool) {
        for (int i = 0; i < 5; i++) {
            Integer r = pool.invoke(new ConstTask(i));
            check(r != null && r == i, "iteration " + i + " returned " + r);
        }
    }

    static void testNullTaskThrowsNPE(ForkJoinPool pool) {
        boolean thrown = false;
        try {
            pool.invoke((ForkJoinTask<Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null task");
    }

    static void testFailingTaskPropagates(ForkJoinPool pool) {
        Throwable caught = null;
        try {
            pool.invoke(new FailingTask());
        } catch (Throwable t) {
            caught = t;
        }
        check(caught instanceof RuntimeException,
              "expected RuntimeException from failing task, got " + caught);
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool(2);
        try {
            testReturnsResult(pool);
            testRepeatedInvocations(pool);
            testNullTaskThrowsNPE(pool);
            testFailingTaskPropagates(pool);
        } finally {
            pool.shutdown();
        }
    }
}
