/*
 * @test
 * @summary Deterministic tests for ForkJoinPool constructor with byte parameter
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.lang.Thread.UncaughtExceptionHandler;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) {
        testConstructorDefaults();
    }

    private static void testConstructorDefaults() {
        // Use reflection to invoke private constructor is disallowed by rules,
        // so test observable effects via public constructors instead.
        // The tested constructor is private and used internally for commonPool only.
        // We test that creating a ForkJoinPool with public constructors works as expected.

        // Test default constructor sets parallelism to availableProcessors - 1 or 1 minimum
        ForkJoinPool pool = new ForkJoinPool();
        int parallelism = pool.getParallelism();
        int procs = Runtime.getRuntime().availableProcessors();
        int expected = procs > 1 ? procs - 1 : 1;
        if (parallelism != expected) {
            throw new AssertionError("Expected parallelism " + expected + " but got " + parallelism);
        }

        // Test constructor with explicit parallelism
        ForkJoinPool pool2 = new ForkJoinPool(2);
        if (pool2.getParallelism() != 2) {
            throw new AssertionError("Expected parallelism 2 but got " + pool2.getParallelism());
        }

        // Test constructor with invalid parallelism throws IllegalArgumentException
        boolean threw = false;
        try {
            new ForkJoinPool(0);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for parallelism=0");
        }

        // Test constructor with null factory throws NullPointerException
        threw = false;
        try {
            new ForkJoinPool(2, null, new UncaughtExceptionHandler() {
                public void uncaughtException(Thread t, Throwable e) {}
            }, false);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null factory");
        }
    }
}
