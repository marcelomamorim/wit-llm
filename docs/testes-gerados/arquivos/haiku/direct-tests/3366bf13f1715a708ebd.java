/*
 * @test
 * @summary Tests for Random.ints(long streamSize): negative size rejection, zero size, produced length and seed determinism
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testMinLongSizeThrows();
        testZeroSizeIsEmpty();
        testProducesRequestedLength();
        testSameSeedSameSequence();
        testDifferentSeedDifferentSequence();
    }

    static void testNegativeSizeThrows() {
        try {
            new Random(1L).ints(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMinLongSizeThrows() {
        try {
            new Random(1L).ints(Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroSizeIsEmpty() {
        int[] values = new Random(42L).ints(0L).toArray();
        if (values.length != 0) {
            throw new RuntimeException("Expected empty stream, got length " + values.length);
        }
    }

    static void testProducesRequestedLength() {
        int[] values = new Random(42L).ints(10L).toArray();
        if (values.length != 10) {
            throw new RuntimeException("Expected length 10, got " + values.length);
        }
    }

    static void testSameSeedSameSequence() {
        int[] a = new Random(12345L).ints(20L).toArray();
        int[] b = new Random(12345L).ints(20L).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("Same seed produced different sequences");
        }
    }

    static void testDifferentSeedDifferentSequence() {
        int[] a = new Random(1L).ints(20L).toArray();
        int[] b = new Random(2L).ints(20L).toArray();
        if (Arrays.equals(a, b)) {
            throw new RuntimeException("Different seeds produced identical sequences");
        }
    }
}
