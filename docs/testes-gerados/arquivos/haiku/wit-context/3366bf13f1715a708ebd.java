/*
 * @test
 * @summary Witup-derived tests for Random.ints(long streamSize)
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testNegativeMinValueThrows();
        testZeroSizeProducesEmptyStream();
        testExactStreamSize();
        testDeterministicForSameSeed();
    }

    static void testNegativeSizeThrows() {
        boolean thrown = false;
        try {
            new Random(1L).ints(-1L);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("ints(-1) did not throw IllegalArgumentException");
        }
    }

    static void testNegativeMinValueThrows() {
        boolean thrown = false;
        try {
            new Random(1L).ints(Long.MIN_VALUE);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("ints(Long.MIN_VALUE) did not throw IllegalArgumentException");
        }
    }

    static void testZeroSizeProducesEmptyStream() {
        long count = new Random(1L).ints(0L).count();
        if (count != 0L) {
            throw new RuntimeException("expected 0 elements, got " + count);
        }
    }

    static void testExactStreamSize() {
        long count = new Random(7L).ints(10L).count();
        if (count != 10L) {
            throw new RuntimeException("expected 10 elements, got " + count);
        }
    }

    static void testDeterministicForSameSeed() {
        int[] a = new Random(123L).ints(20L).toArray();
        int[] b = new Random(123L).ints(20L).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("streams with same seed differ");
        }
    }
}
