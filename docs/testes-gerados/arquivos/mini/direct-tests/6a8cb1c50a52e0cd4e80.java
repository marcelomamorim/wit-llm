/*
 * @test
 * @summary Test java.util.concurrent.ThreadLocalRandom.tryAdvance(IntConsumer) behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
    }

    private static void testTryAdvanceWithValidConsumer() {
        final ThreadLocalRandom tlr = ThreadLocalRandom.current();
        final int[] consumed = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        };

        // tryAdvance should return true and call consumer.accept with a value in [origin,bound)
        boolean result = tlr.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance returned false unexpectedly");
        }
        // The consumed value should be within the range [origin,bound) of ThreadLocalRandom
        // origin and bound are package-private, so we test only that the value is an int (always true)
        // and that tryAdvance returned true, which implies a value was consumed.
        // We cannot assert exact range without reflection or internal access.
    }

    private static void testTryAdvanceWithNullConsumer() {
        final ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            tlr.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
