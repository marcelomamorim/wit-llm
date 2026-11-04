/*
 * @test
 * @summary Test ForkJoinPool() constructor for expected exceptions and normal behavior
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) {
        testConstructorNormal();
    }

    private static void testConstructorNormal() {
        // The default constructor uses Runtime.getRuntime().availableProcessors() bounded by MAX_CAP (0x7fff)
        // and defaultForkJoinWorkerThreadFactory which should not be null in normal environment.
        // We test that the constructor completes normally without throwing.
        ForkJoinPool pool = null;
        try {
            pool = new ForkJoinPool();
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new AssertionError("Unexpected exception from ForkJoinPool() constructor: " + e);
        }
        if (pool == null) {
            throw new AssertionError("ForkJoinPool() constructor returned null");
        }
    }
}
