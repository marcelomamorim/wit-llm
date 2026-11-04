/*
 * @test
 * @summary Verify SplittableRandom double spliterator tryAdvance behavior (null consumer, sequence, exhaustion, range)
 * @run main SplittableRandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testSequenceMatchesStream();
        testExhaustionReturnsFalseAndDoesNotInvokeConsumer();
        testValuesWithinDefaultRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfDouble s = new SplittableRandom(1L).doubles(2).spliterator();
        try {
            s.tryAdvance((DoubleConsumer) null);
            throw new RuntimeException("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSequenceMatchesStream() {
        double[] expected = new SplittableRandom(42L).doubles(3).toArray();
        List<Double> collected = new ArrayList<>();
        Spliterator.OfDouble s = new SplittableRandom(42L).doubles(3).spliterator();
        DoubleConsumer c = v -> collected.add(v);
        int trues = 0;
        while (s.tryAdvance(c)) {
            trues++;
        }
        check(trues == 3, "Expected 3 advances but got " + trues);
        check(collected.size() == expected.length, "Size mismatch");
        for (int i = 0; i < expected.length; i++) {
            check(Double.compare(expected[i], collected.get(i)) == 0,
                  "Mismatch at index " + i + ": expected " + expected[i] + " got " + collected.get(i));
        }
    }

    static void testExhaustionReturnsFalseAndDoesNotInvokeConsumer() {
        Spliterator.OfDouble s = new SplittableRandom(7L).doubles(2).spliterator();
        int[] calls = new int[1];
        DoubleConsumer c = v -> calls[0]++;
        int trues = 0;
        for (int i = 0; i < 5; i++) {
            if (s.tryAdvance(c)) {
                trues++;
            }
        }
        check(trues == 2, "Expected exactly 2 true results but got " + trues);
        check(calls[0] == 2, "Expected consumer invoked 2 times but got " + calls[0]);
    }

    static void testValuesWithinDefaultRange() {
        Spliterator.OfDouble s = new SplittableRandom(123L).doubles(1000).spliterator();
        int[] count = new int[1];
        DoubleConsumer c = v -> {
            check(v >= 0.0 && v < 1.0, "Value out of [0,1): " + v);
            count[0]++;
        };
        while (s.tryAdvance(c)) {
            // consume all
        }
        check(count[0] == 1000, "Expected 1000 values but got " + count[0]);
    }
}
