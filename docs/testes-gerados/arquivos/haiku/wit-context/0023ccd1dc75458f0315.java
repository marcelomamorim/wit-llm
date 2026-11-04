/*
 * @test
 * @summary WITUP-derived tests for ThreadLocalRandom.ints(long) argument validation and stream size
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows(-1L);
        testNegativeSizeThrows(Long.MIN_VALUE);
        testZeroSizeIsEmpty();
        testPositiveSizeProducesExpectedCount(1L);
        testPositiveSizeProducesExpectedCount(16L);
    }

    private static void testNegativeSizeThrows(long size) {
        boolean thrown = false;
        try {
            IntStream s = ThreadLocalRandom.current().ints(size);
            s.count();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for negative stream size: " + size);
        }
    }

    private static void testZeroSizeIsEmpty() {
        long count = ThreadLocalRandom.current().ints(0L).count();
        if (count != 0L) {
            throw new RuntimeException("expected empty stream for size 0 but got count: " + count);
        }
    }

    private static void testPositiveSizeProducesExpectedCount(long size) {
        long count = ThreadLocalRandom.current().ints(size).count();
        if (count != size) {
            throw new RuntimeException("expected count " + size + " but got " + count);
        }
    }
}
