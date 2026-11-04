/*
 * @test
 * @summary Test private ForkJoinPool(byte forCommonPoolOnly) constructor behavior
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.lang.reflect.Constructor;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to access private constructor ForkJoinPool(byte)
        Constructor<ForkJoinPool> ctor = ForkJoinPool.class.getDeclaredConstructor(byte.class);
        ctor.setAccessible(true);

        // Create instance with arbitrary byte value (e.g. 0)
        ForkJoinPool pool = ctor.newInstance((byte)0);

        // Check that pool is not null
        if (pool == null) {
            throw new AssertionError("ForkJoinPool instance is null");
        }

        // Check that parallelism mode is within expected range
        // mode is private, so we cannot access directly; instead, submit a task and check it runs
        final int[] result = new int[1];
        pool.submit(() -> result[0] = 42).get();
        if (result[0] != 42) {
            throw new AssertionError("Task did not execute correctly");
        }

        // Check that workerNamePrefix is set to expected value by submitting a task that queries thread name
        final String[] threadName = new String[1];
        pool.submit(() -> threadName[0] = Thread.currentThread().getName()).get();
        if (threadName[0] == null || !threadName[0].startsWith("ForkJoinPool.commonPool-worker-")) {
            throw new AssertionError("Worker thread name prefix incorrect: " + threadName[0]);
        }

        // Check that no exception was thrown during construction and basic usage
        System.out.println("ForkJoinPool(byte) constructor test passed.");
    }
}
