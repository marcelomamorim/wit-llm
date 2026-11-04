/*
 * @test
 * @summary Test java.util.concurrent.ThreadLocalRandom.tryAdvance(DoubleConsumer) behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceReturnsTrueWhenIndexLessThanFence();
        testTryAdvanceReturnsFalseWhenIndexNotLessThanFence();
    }

    private static void testTryAdvanceWithNullConsumer() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            tlr.tryAdvance(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceReturnsTrueWhenIndexLessThanFence() {
        // We cannot set index and fence directly, so we test the observable behavior:
        // tryAdvance returns true and calls consumer.accept with a double in [origin,bound).
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        final boolean[] called = {false};
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                called[0] = true;
                if (value < 0.0 || value >= 1.0) {
                    throw new Error("Value out of expected range [0.0,1.0): " + value);
                }
            }
        };

        // Because we cannot set index and fence, we rely on the default state where index < fence
        // and tryAdvance should return true and call consumer.accept
        boolean result = tlr.tryAdvance(consumer);
        if (!result) {
            throw new Error("tryAdvance should return true when index < fence");
        }
        if (!called[0]) {
            throw new Error("Consumer.accept was not called");
        }
    }

    private static void testTryAdvanceReturnsFalseWhenIndexNotLessThanFence() {
        // We cannot directly set index and fence, so we test that tryAdvance eventually returns false
        // by calling tryAdvance repeatedly until it returns false.
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                // no-op
            }
        };

        boolean result = true;
        int attempts = 0;
        while (result && attempts < 10000) {
            result = tlr.tryAdvance(consumer);
            attempts++;
        }

        if (result) {
            throw new Error("tryAdvance did not return false after many attempts");
        }
    }
}
