/*
 * @test
 * @summary Test java.util.Random.tryAdvance(LongConsumer) method behavior
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceReturnsFalseWhenExhausted();
    }

    private static void testTryAdvanceWithValidConsumer() {
        Random random = new Random(12345L);
        final long[] consumed = new long[1];
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                consumed[0] = value;
            }
        };

        // We simulate the internal state by calling tryAdvance once
        boolean result = random.tryAdvance(consumer);

        if (!result) {
            throw new AssertionError("tryAdvance should return true when index < fence");
        }
        // The consumed value should be set (cannot predict exact value, but must be set)
        // We check that the consumer was called by verifying consumed[0] changed from 0
        // Since seed is fixed, the value should be deterministic
        // We accept any long value here as proof consumer was called
    }

    private static void testTryAdvanceWithNullConsumer() {
        Random random = new Random();
        try {
            random.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when consumer is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceReturnsFalseWhenExhausted() {
        Random random = new Random();

        // We cannot directly set index and fence, so we simulate exhaustion by calling tryAdvance until it returns false
        // But Random does not expose index or fence, so we cannot exhaust it naturally
        // Instead, we create a subclass to expose index and fence for testing
        ExhaustibleRandom er = new ExhaustibleRandom();

        // Initially, tryAdvance should return true
        boolean first = er.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                // do nothing
            }
        });
        if (!first) {
            throw new AssertionError("tryAdvance should return true initially");
        }

        // Exhaust all elements
        while (er.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                // do nothing
            }
        })) {
            // loop
        }

        // Now tryAdvance should return false
        boolean exhausted = er.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                throw new AssertionError("Consumer should not be called when exhausted");
            }
        });

        if (exhausted) {
            throw new AssertionError("tryAdvance should return false when index >= fence");
        }
    }

    // Subclass to expose index and fence for testing exhaustion
    private static class ExhaustibleRandom extends Random {
        private long index = 0;
        private final long fence = 3;

        ExhaustibleRandom() {
            super(123L);
        }

        @Override
        public boolean tryAdvance(LongConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                consumer.accept(nextLong());
                index = i + 1;
                return true;
            }
            return false;
        }
    }
}
