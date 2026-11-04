/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.forEachRemaining(LongConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingConsumesCorrectly();
    }

    static void testForEachRemainingNullConsumer() {
        ThreadLocalRandom.RandomLongsSpliterator spliterator = new ThreadLocalRandom.RandomLongsSpliterator(0L, 5L, 0L, 5L);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testForEachRemainingConsumesCorrectly() {
        final long origin = 10L;
        final long bound = 15L;
        final int size = (int)(bound - origin);
        ThreadLocalRandom.RandomLongsSpliterator spliterator = new ThreadLocalRandom.RandomLongsSpliterator(origin, bound, 0L, size);

        final long[] results = new long[size];
        final int[] index = {0};

        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                if (value < origin || value >= bound) {
                    throw new AssertionError("Value out of bounds: " + value);
                }
                if (index[0] >= size) {
                    throw new AssertionError("More values than expected");
                }
                results[index[0]++] = value;
            }
        };

        spliterator.forEachRemaining(consumer);

        if (index[0] != size) {
            throw new AssertionError("Did not consume expected number of elements: " + index[0]);
        }

        // Check that all consumed values are within bounds and no duplicates
        for (int i = 0; i < index[0]; i++) {
            long v = results[i];
            for (int j = i + 1; j < index[0]; j++) {
                if (results[j] == v) {
                    throw new AssertionError("Duplicate value consumed: " + v);
                }
            }
        }
    }
}