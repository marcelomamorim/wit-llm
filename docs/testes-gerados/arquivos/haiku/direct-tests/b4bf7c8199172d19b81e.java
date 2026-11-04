/*
 * @test
 * @summary Behavioral tests for SplittableRandom.longs(long streamSize)
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeSizeThrows() {
        boolean thrown = false;
        try {
            new SplittableRandom(1L).longs(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "longs(-1) must throw IllegalArgumentException");
    }

    static void testMinNegativeSizeThrows() {
        boolean thrown = false;
        try {
            new SplittableRandom(1L).longs(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "longs(Long.MIN_VALUE) must throw IllegalArgumentException");
    }

    static void testZeroSizeIsEmpty() {
        long count = new SplittableRandom(7L).longs(0L).count();
        check(count == 0L, "longs(0) must be empty, was " + count);
    }

    static void testExactSize() {
        long count = new SplittableRandom(7L).longs(5L).count();
        check(count == 5L, "longs(5) must have 5 elements, was " + count);
    }

    static void testSameSeedReproduces() {
        long[] a = new SplittableRandom(42L).longs(10L).toArray();
        long[] b = new SplittableRandom(42L).longs(10L).toArray();
        check(a.length == 10, "unexpected length " + a.length);
        check(Arrays.equals(a, b), "same seed must produce same sequence");
    }

    static void testDifferentSeedsDiffer() {
        long[] a = new SplittableRandom(1L).longs(8L).toArray();
        long[] b = new SplittableRandom(2L).longs(8L).toArray();
        check(!Arrays.equals(a, b), "different seeds should produce different sequences");
    }

    static void testValuesNotAllEqual() {
        long[] values = new SplittableRandom(123L).longs(16L).toArray();
        long first = values[0];
        boolean differs = false;
        for (long v : values) {
            if (v != first) {
                differs = true;
                break;
            }
        }
        check(differs, "generated longs should not all be identical");
    }

    static void testStreamIsSequentialAndSized() {
        LongStream s = new SplittableRandom(99L).longs(4L);
        check(!s.isParallel(), "longs(...) is created with parallel=false");
        check(s.count() == 4L, "stream size must be 4");
    }

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testMinNegativeSizeThrows();
        testZeroSizeIsEmpty();
        testExactSize();
        testSameSeedReproduces();
        testDifferentSeedsDiffer();
        testValuesNotAllEqual();
        testStreamIsSequentialAndSized();
    }
}
