/*
 * @test
 * @summary Test java.util.Random.longs(long) method for correct behavior and exception
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testLongsPositiveSize();
        testLongsZeroSize();
        testLongsNegativeSizeThrows();
    }

    private static void testLongsPositiveSize() {
        Random rnd = new Random(12345L);
        LongStream stream = rnd.longs(5L);
        long[] values = stream.toArray();
        if (values.length != 5) {
            throw new AssertionError("Expected stream size 5 but got " + values.length);
        }
        // Check determinism: calling again with same seed and size produces same values
        Random rnd2 = new Random(12345L);
        long[] values2 = rnd2.longs(5L).toArray();
        for (int i = 0; i < 5; i++) {
            if (values[i] != values2[i]) {
                throw new AssertionError("Values differ at index " + i + ": " + values[i] + " != " + values2[i]);
            }
        }
    }

    private static void testLongsZeroSize() {
        Random rnd = new Random();
        LongStream stream = rnd.longs(0L);
        long[] values = stream.toArray();
        if (values.length != 0) {
            throw new AssertionError("Expected empty stream for size 0 but got length " + values.length);
        }
    }

    private static void testLongsNegativeSizeThrows() {
        Random rnd = new Random();
        try {
            rnd.longs(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
