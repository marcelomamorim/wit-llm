/*
 * @test
 * @summary Verifies RandomDoublesSpliterator.tryAdvance via Random.doubles(...).spliterator()
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testSequenceMatchesToArray();
        testExhaustionReturnsFalse();
        testNullConsumerThrowsNPE();
        testBoundedValuesInRange();
    }

    static void testSequenceMatchesToArray() {
        double[] expected = new Random(42).doubles(3).toArray();
        Spliterator.OfDouble sp = new Random(42).doubles(3).spliterator();
        double[] got = new double[3];
        int[] idx = {0};
        for (int i = 0; i < 3; i++) {
            boolean advanced = sp.tryAdvance((DoubleConsumer) d -> got[idx[0]++] = d);
            if (!advanced) {
                throw new RuntimeException("tryAdvance returned false early at " + i);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (Double.compare(got[i], expected[i]) != 0) {
                throw new RuntimeException("Mismatch at " + i + ": " + got[i] + " vs " + expected[i]);
            }
        }
    }

    static void testExhaustionReturnsFalse() {
        Spliterator.OfDouble sp = new Random(7).doubles(2).spliterator();
        int[] count = {0};
        DoubleConsumer counter = d -> count[0]++;
        while (sp.tryAdvance(counter)) {
            // drain
        }
        if (count[0] != 2) {
            throw new RuntimeException("Expected 2 elements, got " + count[0]);
        }
        if (sp.tryAdvance(counter)) {
            throw new RuntimeException("tryAdvance should return false after exhaustion");
        }
        if (count[0] != 2) {
            throw new RuntimeException("Consumer invoked after exhaustion");
        }
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfDouble sp = new Random(1).doubles(1).spliterator();
        try {
            sp.tryAdvance((DoubleConsumer) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testBoundedValuesInRange() {
        Spliterator.OfDouble sp = new Random(99).doubles(5, 1.0, 2.0).spliterator();
        int[] count = {0};
        while (sp.tryAdvance((DoubleConsumer) d -> {
            if (d < 1.0 || d >= 2.0) {
                throw new RuntimeException("Value out of bounds: " + d);
            }
            count[0]++;
        })) {
            // drain
        }
        if (count[0] != 5) {
            throw new RuntimeException("Expected 5 bounded values, got " + count[0]);
        }
    }
}
