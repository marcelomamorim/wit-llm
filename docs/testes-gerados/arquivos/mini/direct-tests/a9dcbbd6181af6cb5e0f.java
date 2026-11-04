/*
 * @test
 * @summary Test java.util.SplittableRandom.forEachRemaining(LongConsumer) behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        final StringBuilder sb = new StringBuilder();
        SplittableRandom rng = new SplittableRandom(12345L);
        // Create a LongConsumer that appends accepted values to sb
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        };

        // We cannot access index, fence, origin, bound, or rng fields directly,
        // so we test the observable behavior by calling forEachRemaining on a fresh SplittableRandom
        // and verify that it produces some output (non-empty string).

        // The forEachRemaining method is from the Spliterator interface, but SplittableRandom does not
        // implement Spliterator directly in JDK 11, so we test the method directly.

        // Call forEachRemaining with the consumer
        rng.forEachRemaining(consumer);

        // The consumer should have accepted some values, so sb length should be > 0
        if (sb.length() == 0) {
            throw new AssertionError("forEachRemaining did not invoke consumer.accept as expected");
        }

        // Also check that the string contains at least one comma (multiple values)
        if (sb.indexOf(",") < 0) {
            throw new AssertionError("forEachRemaining consumer output format unexpected");
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        SplittableRandom rng = new SplittableRandom();
        try {
            rng.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException when consumer is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
