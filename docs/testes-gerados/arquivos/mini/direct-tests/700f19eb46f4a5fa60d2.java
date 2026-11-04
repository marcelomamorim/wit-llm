/*
 * @test
 * @summary Test ThreadLocalRandom.tryAdvance(LongConsumer) behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceReturnsTrueAndConsumes();
        testTryAdvanceReturnsFalseWhenExhausted();
        testTryAdvanceThrowsNPEOnNullConsumer();
    }

    static void testTryAdvanceReturnsTrueAndConsumes() {
        final ThreadLocalRandom tlr = ThreadLocalRandom.current();
        final long origin = 0L;
        final long bound = 10L;

        // We create a custom subclass to access and set index and fence fields
        // but since these fields are not accessible, we test the observable behavior only

        // We test that tryAdvance returns true at least once and calls consumer.accept with a value in [origin,bound)
        final long[] consumed = new long[1];
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                consumed[0] = value;
            }
        };

        // We call tryAdvance repeatedly until it returns false or 100 times max
        // to avoid infinite loop in case of bug
        boolean advanced = false;
        for (int i = 0; i < 100; i++) {
            // We call tryAdvance on the current ThreadLocalRandom
            // The method uses internalNextLong(origin,bound) internally
            // We expect it to return true and call consumer.accept
            boolean result = tlr.tryAdvance(consumer);
            if (result) {
                advanced = true;
                // Check that consumed value is in [origin,bound)
                if (consumed[0] < origin || consumed[0] >= bound) {
                    throw new AssertionError("Consumed value out of bounds: " + consumed[0]);
                }
            } else {
                // Once false is returned, no more advances
                break;
            }
        }
        if (!advanced) {
            throw new AssertionError("tryAdvance never returned true");
        }
    }

    static void testTryAdvanceReturnsFalseWhenExhausted() {
        // We cannot directly set index and fence, so we test that after many calls tryAdvance returns false
        final ThreadLocalRandom tlr = ThreadLocalRandom.current();
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                // no-op
            }
        };

        boolean result = true;
        int count = 0;
        while (result && count < 10000) {
            result = tlr.tryAdvance(consumer);
            count++;
        }
        if (result) {
            throw new AssertionError("tryAdvance did not return false after many calls");
        }
    }

    static void testTryAdvanceThrowsNPEOnNullConsumer() {
        final ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            tlr.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
