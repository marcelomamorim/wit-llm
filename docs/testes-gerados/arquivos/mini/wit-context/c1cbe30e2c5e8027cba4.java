/*
 * @test
 * @summary Test SplittableRandom.RandomIntsSpliterator.forEachRemaining(IntConsumer) for NPE and normal behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullConsumer() {
        SplittableRandom rng = new SplittableRandom(12345L);
        SplittableRandom.RandomIntsSpliterator spliterator = rng.ints().spliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        SplittableRandom rng = new SplittableRandom(12345L);
        SplittableRandom.RandomIntsSpliterator spliterator = rng.ints(5).spliterator();
        final int[] results = new int[5];
        final int[] index = {0};
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                if (index[0] >= results.length) {
                    throw new AssertionError("Consumer called more times than expected");
                }
                results[index[0]++] = value;
            }
        };
        spliterator.forEachRemaining(consumer);
        if (index[0] != 5) {
            throw new AssertionError("Consumer was not called expected number of times: " + index[0]);
        }
        // Check that all values are within the expected bound (default bound is Integer.MAX_VALUE)
        for (int v : results) {
            if (v < 0) {
                throw new AssertionError("Generated value out of expected range: " + v);
            }
        }
    }
}
