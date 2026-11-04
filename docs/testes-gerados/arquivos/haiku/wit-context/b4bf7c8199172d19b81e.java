/*
 * @test
 * @summary WITUP-derived tests for SplittableRandom.longs(long streamSize)
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrowsIllegalArgumentException();
        testMinValueSizeThrowsIllegalArgumentException();
        testZeroSizeProducesEmptyStream();
        testPositiveSizeProducesExactCount();
        testSameSeedProducesSameSequence();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeSizeThrowsIllegalArgumentException() {
        SplittableRandom random = new SplittableRandom(1L);
        try {
            random.longs(-1L);
            throw new RuntimeException("longs(-1) should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMinValueSizeThrowsIllegalArgumentException() {
        SplittableRandom random = new SplittableRandom(2L);
        try {
            random.longs(Long.MIN_VALUE);
            throw new RuntimeException("longs(Long.MIN_VALUE) should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroSizeProducesEmptyStream() {
        SplittableRandom random = new SplittableRandom(3L);
        long count = random.longs(0L).count();
        check(count == 0L, "longs(0) should produce an empty stream, got " + count);
    }

    static void testPositiveSizeProducesExactCount() {
        SplittableRandom random = new SplittableRandom(4L);
        long count = random.longs(17L).count();
        check(count == 17L, "longs(17) should produce 17 elements, got " + count);
    }

    static void testSameSeedProducesSameSequence() {
        long[] first = new SplittableRandom(42L).longs(8L).toArray();
        long[] second = new SplittableRandom(42L).longs(8L).toArray();
        check(first.length == 8, "expected 8 elements, got " + first.length);
        check(Arrays.equals(first, second), "same seed should produce identical sequences");
    }
}
