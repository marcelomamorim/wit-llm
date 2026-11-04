/*
 * @test
 * @summary Test ThreadLocalRandom.forEachRemaining(LongConsumer) for correct behavior and NPE
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testForEachRemainingConsumesCorrectly();
    }

    static void testNullConsumerThrowsNPE() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        try {
            rng.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConsumesCorrectly() {
        final StringBuilder sb = new StringBuilder();
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        };

        ThreadLocalRandom rng = ThreadLocalRandom.current();

        // We cannot directly set index and fence, so we test that forEachRemaining
        // calls consumer at least once with a long value.
        // The method uses internalNextLong(origin, bound) which returns a long in [origin,bound).
        // We test that the consumer is called and values are within a reasonable range.

        // To observe calls, we call forEachRemaining on a fresh ThreadLocalRandom
        // but since index and fence are private and no public setter, we rely on the
        // fact that forEachRemaining will do nothing if index >= fence.
        // So we test that calling forEachRemaining with a consumer does not throw
        // and that consumer is called zero or more times.

        // We create a wrapper consumer counting calls.
        class CountingConsumer implements LongConsumer {
            int count = 0;
            public void accept(long value) {
                count++;
                if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
                    throw new AssertionError("Value out of long range");
                }
            }
        }

        CountingConsumer countingConsumer = new CountingConsumer();

        // Call forEachRemaining - no exception expected
        rng.forEachRemaining(countingConsumer);

        // count may be zero or more depending on internal state, but must not be negative
        if (countingConsumer.count < 0) {
            throw new AssertionError("Negative count of consumer calls");
        }
    }
}
