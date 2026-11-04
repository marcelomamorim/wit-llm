/*
 * @test
 * @summary Test SplittableRandom.RandomLongsSpliterator.forEachRemaining(LongConsumer) for NPE and normal behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullConsumer() {
        SplittableRandom rng = new SplittableRandom(12345L);
        SplittableRandom.RandomLongsSpliterator spliterator = new SplittableRandom.RandomLongsSpliterator(rng, 0L, 10L, 0L, 5L);
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        SplittableRandom rng = new SplittableRandom(12345L);
        SplittableRandom.RandomLongsSpliterator spliterator = new SplittableRandom.RandomLongsSpliterator(rng, 0L, 10L, 0L, 3L);
        final long[] results = new long[3];
        final int[] index = {0};
        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                results[index[0]++] = value;
            }
        });
        if (index[0] != 3) {
            throw new Error("Expected 3 values consumed, got " + index[0]);
        }
        for (int i = 0; i < 3; i++) {
            if (results[i] < 0 || results[i] >= 10) {
                throw new Error("Value out of expected range: " + results[i]);
            }
        }
    }
}
