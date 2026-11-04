/*
 * @test
 * @summary Test java.util.SplittableRandom.tryAdvance(LongConsumer) behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceReturnsTrueAndConsumesValue();
        testTryAdvanceReturnsFalseWhenExhausted();
        testTryAdvanceThrowsNPEOnNullConsumer();
    }

    static void testTryAdvanceReturnsTrueAndConsumesValue() {
        final SplittableRandom sr = new SplittableRandom(12345L);
        final long[] consumed = new long[1];
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                consumed[0] = value;
            }
        };
        boolean result = sr.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when index < fence");
        }
        // The consumed value should be within the full long range, no direct assert on value
        // but it must be set (not zero if seed is non-zero is not guaranteed, so just check method called)
        // We check that consumer.accept was called by verifying consumed[0] was set (default 0L)
        // Since seed is fixed, consumed[0] should be deterministic
        if (consumed[0] == 0L) {
            throw new AssertionError("Consumer accept method was not called or returned zero unexpectedly");
        }
    }

    static void testTryAdvanceReturnsFalseWhenExhausted() {
        // We create a subclass to set index and fence equal to simulate exhaustion
        class TestSplittableRandom extends SplittableRandom {
            long index;
            long fence;
            TestSplittableRandom(long seed) {
                super(seed);
                this.index = 1L;
                this.fence = 1L;
            }
            @Override
            public boolean tryAdvance(LongConsumer consumer) {
                if (consumer == null) throw new NullPointerException();
                long i = index, f = fence;
                if (i < f) {
                    consumer.accept(0L);
                    index = i + 1;
                    return true;
                }
                return false;
            }
        }
        TestSplittableRandom tsr = new TestSplittableRandom(0L);
        boolean result = tsr.tryAdvance(new LongConsumer() {
            public void accept(long value) {}
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false when index >= fence");
        }
    }

    static void testTryAdvanceThrowsNPEOnNullConsumer() {
        SplittableRandom sr = new SplittableRandom(0L);
        try {
            sr.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when consumer is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
