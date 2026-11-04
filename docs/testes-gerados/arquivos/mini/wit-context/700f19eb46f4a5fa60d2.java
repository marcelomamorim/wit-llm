/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.tryAdvance(LongConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenExhausted();
    }

    private static void testTryAdvanceWithNullConsumer() {
        ThreadLocalRandom.RandomLongsSpliterator spliterator = new ThreadLocalRandom.RandomLongsSpliterator(0L, 1L, 0L, 1L);
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when consumer is null");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        final long[] consumed = new long[1];
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                consumed[0] = value;
            }
        };
        ThreadLocalRandom.RandomLongsSpliterator spliterator = new ThreadLocalRandom.RandomLongsSpliterator(0L, 10L, 0L, 1L);
        boolean advanced = spliterator.tryAdvance(consumer);
        if (!advanced) {
            throw new AssertionError("Expected tryAdvance to return true when index < fence");
        }
        // The consumed value should be within the origin and bound range
        if (consumed[0] < 0L || consumed[0] >= 10L) {
            throw new AssertionError("Consumed value " + consumed[0] + " out of expected range [0,10)");
        }
    }

    private static void testTryAdvanceWhenExhausted() {
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                // no-op
            }
        };
        // Create spliterator with index == fence to simulate exhaustion
        ThreadLocalRandom.RandomLongsSpliterator spliterator = new ThreadLocalRandom.RandomLongsSpliterator(0L, 10L, 1L, 1L);
        boolean advanced = spliterator.tryAdvance(consumer);
        if (advanced) {
            throw new AssertionError("Expected tryAdvance to return false when index >= fence");
        }
    }
}
