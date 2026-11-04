/*
 * @test
 * @summary Verifies argument validation and observable determinism of Random.longs(long streamSize)
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNegativeSizeThrowsIllegalArgumentException();
        testMinValueSizeThrowsIllegalArgumentException();
        testZeroSizeProducesEmptyStream();
        testStreamSizeIsHonored();
        testSameSeedProducesSameSequence();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeSizeThrowsIllegalArgumentException() {
        boolean thrown = false;
        try {
            new Random(1L).longs(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "longs(-1) must throw IllegalArgumentException");
    }

    static void testMinValueSizeThrowsIllegalArgumentException() {
        boolean thrown = false;
        try {
            new Random(2L).longs(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "longs(Long.MIN_VALUE) must throw IllegalArgumentException");
    }

    static void testZeroSizeProducesEmptyStream() {
        long count = new Random(3L).longs(0L).count();
        check(count == 0L, "longs(0) must produce an empty stream, got " + count);
    }

    static void testStreamSizeIsHonored() {
        long[] values = new Random(4L).longs(5L).toArray();
        check(values.length == 5, "longs(5) must produce 5 elements, got " + values.length);
    }

    static void testSameSeedProducesSameSequence() {
        long[] first = new Random(42L).longs(16L).toArray();
        long[] second = new Random(42L).longs(16L).toArray();
        check(Arrays.equals(first, second),
              "Random instances with the same seed must produce identical longs sequences");
    }
}
