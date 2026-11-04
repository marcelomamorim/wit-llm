/*
 * @test
 * @summary Behavioral checks for SplittableRandom spliterator tryAdvance(LongConsumer) through public APIs
 * @run main SplittableRandomWitupTest
 */

import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testZeroSizeReturnsFalseWithoutConsuming();
        testExhaustionReturnsFalseAndConsumesExactlyCount();
        testDeterministicSequenceMatchesStream();
    }

    static Spliterator.OfLong spliteratorOf(long seed, long size) {
        return new SplittableRandom(seed).longs(size).spliterator();
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong sp = spliteratorOf(1L, 3L);
        try {
            sp.tryAdvance((LongConsumer) null);
            throw new RuntimeException("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testZeroSizeReturnsFalseWithoutConsuming() {
        Spliterator.OfLong sp = spliteratorOf(2L, 0L);
        final int[] calls = {0};
        boolean advanced = sp.tryAdvance((LongConsumer) v -> calls[0]++);
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false for empty stream");
        }
        if (calls[0] != 0) {
            throw new RuntimeException("Consumer must not be called, calls=" + calls[0]);
        }
    }

    static void testExhaustionReturnsFalseAndConsumesExactlyCount() {
        Spliterator.OfLong sp = spliteratorOf(42L, 2L);
        final int[] calls = {0};
        LongConsumer counter = v -> calls[0]++;
        if (!sp.tryAdvance(counter)) {
            throw new RuntimeException("First tryAdvance should return true");
        }
        if (!sp.tryAdvance(counter)) {
            throw new RuntimeException("Second tryAdvance should return true");
        }
        if (sp.tryAdvance(counter)) {
            throw new RuntimeException("Third tryAdvance should return false");
        }
        if (calls[0] != 2) {
            throw new RuntimeException("Expected 2 consumed values, got " + calls[0]);
        }
        if (sp.tryAdvance(counter)) {
            throw new RuntimeException("Spliterator should remain exhausted");
        }
        if (calls[0] != 2) {
            throw new RuntimeException("Consumer called after exhaustion, calls=" + calls[0]);
        }
    }

    static void testDeterministicSequenceMatchesStream() {
        final long seed = 7L;
        final int n = 5;
        long[] expected = new SplittableRandom(seed).longs(n).toArray();

        Spliterator.OfLong sp = spliteratorOf(seed, n);
        long[] actual = new long[n];
        for (int i = 0; i < n; i++) {
            final int idx = i;
            if (!sp.tryAdvance((LongConsumer) v -> actual[idx] = v)) {
                throw new RuntimeException("Premature end at index " + i);
            }
        }
        if (sp.tryAdvance((LongConsumer) v -> { })) {
            throw new RuntimeException("Expected end after " + n + " values");
        }
        for (int i = 0; i < n; i++) {
            if (expected[i] != actual[i]) {
                throw new RuntimeException("Mismatch at " + i + ": expected "
                        + expected[i] + " got " + actual[i]);
            }
        }
    }
}
