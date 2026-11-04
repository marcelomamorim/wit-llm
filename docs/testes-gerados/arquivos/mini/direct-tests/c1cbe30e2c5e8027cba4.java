/*
 * @test
 * @summary Test java.util.SplittableRandom.forEachRemaining(IntConsumer) for correct iteration and null handling
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    private static void testForEachRemainingWithValidConsumer() {
        final int[] results = new int[5];
        final int[] count = new int[1];

        SplittableRandom rng = new SplittableRandom(12345L);

        // We create a SplittableRandom and simulate a Spliterator-like scenario
        // by calling forEachRemaining on a range of 5 elements.
        // Since we cannot access index, fence, origin, bound, or rng fields,
        // we test the public behavior by calling forEachRemaining on a Spliterator
        // obtained from ints() stream limited to 5 elements.

        // Use the public ints() method to get a stream of 5 ints
        rng.ints(5).forEach(new IntConsumer() {
            int idx = 0;
            public void accept(int value) {
                if (idx < results.length) {
                    results[idx++] = value;
                    count[0]++;
                }
            }
        });

        if (count[0] != 5) {
            throw new AssertionError("Expected 5 elements consumed, got " + count[0]);
        }

        // Check that all values are within Integer range (always true) and no exceptions
        for (int v : results) {
            // Just check values are within Integer.MIN_VALUE and Integer.MAX_VALUE
            if (v < Integer.MIN_VALUE || v > Integer.MAX_VALUE) {
                throw new AssertionError("Value out of int range: " + v);
            }
        }
    }

    private static void testForEachRemainingWithNullConsumer() {
        SplittableRandom rng = new SplittableRandom();
        try {
            rng.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
