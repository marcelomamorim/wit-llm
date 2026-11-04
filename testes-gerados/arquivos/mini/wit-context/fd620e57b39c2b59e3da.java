/*
 * @test
 * @summary Test deterministic behavior and exception of SplittableRandom.ints(long)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testIntsWithValidSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    private static void testIntsWithValidSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        IntStream stream = sr.ints(5L);
        int[] values = stream.toArray();
        if (values.length != 5) {
            throw new AssertionError("Expected stream size 5 but got " + values.length);
        }
        // Check determinism: repeated call with same seed and size yields same values
        SplittableRandom sr2 = new SplittableRandom(12345L);
        int[] values2 = sr2.ints(5L).toArray();
        for (int i = 0; i < 5; i++) {
            if (values[i] != values2[i]) {
                throw new AssertionError("Values differ at index " + i + ": " + values[i] + " vs " + values2[i]);
            }
        }
    }

    private static void testIntsWithZeroSize() {
        SplittableRandom sr = new SplittableRandom(0L);
        IntStream stream = sr.ints(0L);
        int[] values = stream.toArray();
        if (values.length != 0) {
            throw new AssertionError("Expected empty stream for size 0 but got length " + values.length);
        }
    }

    private static void testIntsWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.ints(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
