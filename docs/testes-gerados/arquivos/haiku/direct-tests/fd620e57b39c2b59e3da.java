/*
 * @test
 * @summary Tests observable behavior of SplittableRandom.ints(long streamSize)
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testZeroSizeProducesEmptyStream();
        testExactSizeProducesRequestedCount();
        testCountMatchesRequestedSize();
        testNegativeSizeThrowsIllegalArgument();
        testMinValueSizeThrowsIllegalArgument();
        testSameSeedProducesSameValues();
    }

    static void testZeroSizeProducesEmptyStream() {
        SplittableRandom r = new SplittableRandom(1L);
        int[] values = r.ints(0L).toArray();
        check(values.length == 0, "ints(0) must produce no elements");
    }

    static void testExactSizeProducesRequestedCount() {
        SplittableRandom r = new SplittableRandom(2L);
        int[] values = r.ints(17L).toArray();
        check(values.length == 17, "ints(17) must produce 17 elements");
    }

    static void testCountMatchesRequestedSize() {
        SplittableRandom r = new SplittableRandom(3L);
        long count = r.ints(100L).count();
        check(count == 100L, "ints(100).count() must be 100");
    }

    static void testNegativeSizeThrowsIllegalArgument() {
        SplittableRandom r = new SplittableRandom(4L);
        boolean thrown = false;
        try {
            r.ints(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "ints(-1) must throw IllegalArgumentException");
    }

    static void testMinValueSizeThrowsIllegalArgument() {
        SplittableRandom r = new SplittableRandom(5L);
        boolean thrown = false;
        try {
            r.ints(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "ints(Long.MIN_VALUE) must throw IllegalArgumentException");
    }

    static void testSameSeedProducesSameValues() {
        int[] a = new SplittableRandom(42L).ints(50L).toArray();
        int[] b = new SplittableRandom(42L).ints(50L).toArray();
        check(Arrays.equals(a, b), "same seed must produce same ints stream");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
