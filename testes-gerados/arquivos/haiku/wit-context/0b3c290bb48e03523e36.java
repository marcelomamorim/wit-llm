/*
 * @test
 * @summary Verify null consumer handling and deterministic sequence of Random.doubles spliterator tryAdvance
 * @run main RandomWitupTest
 */
import java.util.Random;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrowsAndDoesNotAdvance();
        testSequenceMatchesStreamForSameSeed();
        testEmptyStreamReturnsFalse();
    }

    static void testNullConsumerThrowsAndDoesNotAdvance() {
        Spliterator.OfDouble sp = new Random(42L).doubles(2).spliterator();
        DoubleConsumer nullConsumer = null;
        try {
            sp.tryAdvance(nullConsumer);
            throw new RuntimeException("NullPointerException expected for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
        double[] holder = new double[1];
        DoubleConsumer store = d -> holder[0] = d;
        if (!sp.tryAdvance(store)) {
            throw new RuntimeException("Spliterator should still have elements after NPE");
        }
    }

    static void testSequenceMatchesStreamForSameSeed() {
        double[] expected = new Random(7L).doubles(3).toArray();
        Spliterator.OfDouble sp = new Random(7L).doubles(3).spliterator();
        double[] actual = new double[3];
        for (int i = 0; i < 3; i++) {
            final int idx = i;
            DoubleConsumer store = d -> actual[idx] = d;
            if (!sp.tryAdvance(store)) {
                throw new RuntimeException("Expected element at index " + i);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("Mismatch at index " + i + ": " + actual[i] + " vs " + expected[i]);
            }
        }
        DoubleConsumer noop = d -> { };
        if (sp.tryAdvance(noop)) {
            throw new RuntimeException("Spliterator should be exhausted after 3 elements");
        }
    }

    static void testEmptyStreamReturnsFalse() {
        Spliterator.OfDouble sp = new Random(1L).doubles(0).spliterator();
        DoubleConsumer noop = d -> { };
        if (sp.tryAdvance(noop)) {
            throw new RuntimeException("Empty stream spliterator should return false");
        }
    }
}
