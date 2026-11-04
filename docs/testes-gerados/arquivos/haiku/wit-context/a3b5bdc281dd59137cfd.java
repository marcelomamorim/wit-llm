/*
 * @test
 * @summary Witup-derived tests for SplittableRandom.doubles(long)
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrowsIllegalArgumentException();
        testMinValueSizeThrowsIllegalArgumentException();
        testZeroSizeProducesEmptyStream();
        testStreamHasRequestedSizeAndRange();
        testSameSeedProducesSameValues();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNegativeSizeThrowsIllegalArgumentException() {
        SplittableRandom random = new SplittableRandom(1L);
        boolean thrown = false;
        try {
            DoubleStream s = random.doubles(-1L);
            s.count();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "doubles(-1) must throw IllegalArgumentException");
    }

    static void testMinValueSizeThrowsIllegalArgumentException() {
        SplittableRandom random = new SplittableRandom(2L);
        boolean thrown = false;
        try {
            random.doubles(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "doubles(Long.MIN_VALUE) must throw IllegalArgumentException");
    }

    static void testZeroSizeProducesEmptyStream() {
        SplittableRandom random = new SplittableRandom(42L);
        long count = random.doubles(0L).count();
        check(count == 0L, "doubles(0) must produce an empty stream, got " + count);
    }

    static void testStreamHasRequestedSizeAndRange() {
        SplittableRandom random = new SplittableRandom(99L);
        double[] values = random.doubles(10L).toArray();
        check(values.length == 10, "doubles(10) must produce 10 values, got " + values.length);
        for (double v : values) {
            check(v >= 0.0 && v < 1.0, "value out of range [0,1): " + v);
        }
    }

    static void testSameSeedProducesSameValues() {
        double[] a = new SplittableRandom(7L).doubles(5L).toArray();
        double[] b = new SplittableRandom(7L).doubles(5L).toArray();
        check(Arrays.equals(a, b), "same seed must produce identical doubles stream");
    }
}
