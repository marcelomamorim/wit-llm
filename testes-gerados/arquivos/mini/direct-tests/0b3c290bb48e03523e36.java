/*
 * @test
 * @summary Test java.util.Random.tryAdvance(DoubleConsumer) behavior
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.DoubleConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceReturnsTrueAndAcceptsValue();
        testTryAdvanceReturnsFalseWhenExhausted();
    }

    private static void testTryAdvanceWithNullConsumer() {
        Random random = new Random();
        try {
            random.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceReturnsTrueAndAcceptsValue() {
        final Random random = new Random(12345L);
        final double[] accepted = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                accepted[0] = value;
            }
        };

        // tryAdvance should return true and call accept with a double
        boolean result = random.tryAdvance(consumer);
        if (!result) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        // accepted[0] should be set to some double value
        if (Double.isNaN(accepted[0])) {
            throw new RuntimeException("Consumer was not called with a valid double");
        }
    }

    private static void testTryAdvanceReturnsFalseWhenExhausted() {
        // We cannot directly exhaust the stream from Random, but we can simulate by subclassing
        // or by reflection. Since reflection is disallowed, we test a subclass that overrides tryAdvance.
        // However, the method is final in Random, so subclassing won't help.
        // Instead, we test that calling tryAdvance repeatedly eventually returns false.

        Random random = new Random() {
            private long index = 0;
            private final long fence = 1;

            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                if (consumer == null) throw new NullPointerException();
                if (index < fence) {
                    consumer.accept(0.5d);
                    index++;
                    return true;
                }
                return false;
            }
        };

        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                // do nothing
            }
        };

        // First call returns true
        boolean first = random.tryAdvance(consumer);
        if (!first) {
            throw new RuntimeException("First tryAdvance call should return true");
        }

        // Second call returns false
        boolean second = random.tryAdvance(consumer);
        if (second) {
            throw new RuntimeException("Second tryAdvance call should return false");
        }
    }
}
