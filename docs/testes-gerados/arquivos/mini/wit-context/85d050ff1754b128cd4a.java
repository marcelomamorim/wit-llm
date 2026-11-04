/*
 * @test
 * @summary Deterministic tests for SplittableRandom.tryAdvance(LongConsumer)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceReturnsTrueWhenIndexLessThanFence();
        testTryAdvanceReturnsFalseWhenIndexEqualsFence();
    }

    private static void testTryAdvanceWithNullConsumer() {
        SplittableRandom rng = new SplittableRandom(12345L);
        try {
            rng.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceReturnsTrueWhenIndexLessThanFence() {
        // We cannot directly set index and fence, so we test by creating a spliterator
        // via SplittableRandom.longs() and calling tryAdvance on it.
        // Since the method under test is SplittableRandom.tryAdvance(LongConsumer),
        // but the source code excerpt shows it is from an inner class RandomLongsSpliterator,
        // we test the public SplittableRandom.longs() stream and its spliterator.

        SplittableRandom rng = new SplittableRandom(12345L);
        final long[] result = new long[1];
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                result[0] = value;
            }
        };

        // Get a spliterator from the longs stream
        java.util.Spliterator.OfLong spliterator = rng.longs(1).spliterator();

        boolean advanced = spliterator.tryAdvance(consumer);
        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        // The consumer should have been called with a value
        // We cannot predict the exact value, but it should be a long
        // Just check that the value was set (not zero is not guaranteed, so skip that)
        // We rely on the fact that the consumer was called once
    }

    private static void testTryAdvanceReturnsFalseWhenIndexEqualsFence() {
        // We cannot directly create a spliterator with index == fence,
        // but we can exhaust the spliterator and then tryAdvance should return false.

        SplittableRandom rng = new SplittableRandom(12345L);
        java.util.Spliterator.OfLong spliterator = rng.longs(1).spliterator();

        // Consume the only element
        spliterator.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                // do nothing
            }
        });

        // Now tryAdvance should return false
        boolean advanced = spliterator.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                throw new RuntimeException("Should not be called");
            }
        });

        if (advanced) {
            throw new RuntimeException("tryAdvance should return false when no elements remain");
        }
    }
}
