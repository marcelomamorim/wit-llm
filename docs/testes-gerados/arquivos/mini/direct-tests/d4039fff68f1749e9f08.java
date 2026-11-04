/*
 * @test
 * @summary Test SplittableRandom.forEachRemaining(DoubleConsumer) behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrows();
        testForEachRemainingConsumesCorrectCount();
    }

    static void testNullConsumerThrows() {
        SplittableRandom rng = new SplittableRandom();
        try {
            rng.doubles(1).forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConsumesCorrectCount() {
        final int count = 5;
        SplittableRandom rng = new SplittableRandom(12345L);
        java.util.SplittableRandom.DoubleStreamImpl stream = (java.util.SplittableRandom.DoubleStreamImpl) rng.doubles(count);

        final int[] consumedCount = {0};
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double d) {
                consumedCount[0]++;
            }
        };

        stream.forEachRemaining(consumer);

        if (consumedCount[0] != count) {
            throw new AssertionError("Expected " + count + " elements consumed, but got " + consumedCount[0]);
        }

        // Calling again should consume zero elements
        consumedCount[0] = 0;
        stream.forEachRemaining(consumer);
        if (consumedCount[0] != 0) {
            throw new AssertionError("Expected 0 elements consumed on second call, but got " + consumedCount[0]);
        }
    }
}
