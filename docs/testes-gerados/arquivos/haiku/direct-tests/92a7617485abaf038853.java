/*
 * @test
 * @summary Verifies Executors.newWorkStealingPool(int) returns a ForkJoinPool in async mode with the requested parallelism and rejects non-positive parallelism
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) throws Exception {
        testReturnsForkJoinPoolWithParallelism();
        testAsyncMode();
        testExecutesSubmittedTask();
        testNonPositiveParallelismRejected();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testReturnsForkJoinPoolWithParallelism() {
        int[] values = {1, 2, 4};
        for (int n : values) {
            ExecutorService es = Executors.newWorkStealingPool(n);
            try {
                check(es instanceof ForkJoinPool, "result should be a ForkJoinPool for n=" + n);
                ForkJoinPool fjp = (ForkJoinPool) es;
                check(fjp.getParallelism() == n, "parallelism should be " + n);
                check(!es.isShutdown(), "new pool should not be shut down");
            } finally {
                es.shutdown();
            }
        }
    }

    static void testAsyncMode() {
        ExecutorService es = Executors.newWorkStealingPool(2);
        try {
            ForkJoinPool fjp = (ForkJoinPool) es;
            check(fjp.getAsyncMode(), "work-stealing pool should use async mode");
        } finally {
            es.shutdown();
        }
    }

    static void testExecutesSubmittedTask() throws Exception {
        ExecutorService es = Executors.newWorkStealingPool(2);
        try {
            Callable<Integer> task = () -> 42;
            int result = es.submit(task).get();
            check(result == 42, "submitted task should return 42 but got " + result);
        } finally {
            es.shutdown();
        }
        check(es.awaitTermination(60, TimeUnit.SECONDS), "pool should terminate after shutdown");
        check(es.isTerminated(), "pool should be terminated");
    }

    static void testNonPositiveParallelismRejected() {
        int[] invalid = {0, -1};
        for (int n : invalid) {
            boolean thrown = false;
            try {
                Executors.newWorkStealingPool(n);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "IllegalArgumentException expected for parallelism=" + n);
        }
    }
}
