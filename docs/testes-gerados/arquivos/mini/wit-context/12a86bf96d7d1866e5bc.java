/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.tryAdvance(DoubleConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        ThreadLocalRandom.RandomDoublesSpliterator spliterator = new ThreadLocalRandom.RandomDoublesSpliterator(0L, 1L, 0L, 1L);
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
        ThreadLocalRandom.RandomDoublesSpliterator spliterator = new ThreadLocalRandom.RandomDoublesSpliterator(0L, 1L, 0L, 1L);
        final double[] consumed = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                consumed[0] = value;
            }
        };

        // Initially index == origin == 0, fence == bound == 1, so tryAdvance should return true once
        boolean advanced = spliterator.tryAdvance(consumer);
        if (!advanced) {
            throw new AssertionError("Expected tryAdvance to return true when index < fence");
        }

        // The consumed value should be within the origin-bound range [0,1)
        if (consumed[0] < 0.0 || consumed[0] >= 1.0) {
            throw new AssertionError("Consumed value " + consumed[0] + " out of expected range [0,1)");
        }

        // After one advance, index == 1, equal to fence, so next tryAdvance should return false
        boolean advancedAgain = spliterator.tryAdvance(consumer);
        if (advancedAgain) {
            throw new AssertionError("Expected tryAdvance to return false when index >= fence");
        }
    }
}
