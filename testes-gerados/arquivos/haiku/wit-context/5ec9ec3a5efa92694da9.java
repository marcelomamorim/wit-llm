/*
 * @test
 * @summary Verify tryAdvance behavior of the int stream spliterator returned by Random.ints(long)
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testNullConsumerThrowsNPEWhenExhausted();
        testAdvancesAndMatchesSequence();
        testEmptyStreamReturnsFalseWithoutAccept();
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfInt sp = new Random(42L).ints(2L).spliterator();
        IntConsumer nullConsumer = null;
        try {
            sp.tryAdvance(nullConsumer);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullConsumerThrowsNPEWhenExhausted() {
        Spliterator.OfInt sp = new Random(7L).ints(0L).spliterator();
        IntConsumer nullConsumer = null;
        try {
            sp.tryAdvance(nullConsumer);
            throw new RuntimeException("Expected NullPointerException on exhausted spliterator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAdvancesAndMatchesSequence() {
        int[] expected = new Random(42L).ints(2L).toArray();
        Spliterator.OfInt sp = new Random(42L).ints(2L).spliterator();
        int[] got = new int[2];
        int[] count = new int[1];
        IntConsumer consumer = v -> {
            if (count[0] < got.length) {
                got[count[0]] = v;
            }
            count[0]++;
        };
        if (!sp.tryAdvance(consumer)) {
            throw new RuntimeException("First tryAdvance should return true");
        }
        if (!sp.tryAdvance(consumer)) {
            throw new RuntimeException("Second tryAdvance should return true");
        }
        if (sp.tryAdvance(consumer)) {
            throw new RuntimeException("Third tryAdvance should return false");
        }
        if (count[0] != 2) {
            throw new RuntimeException("Consumer called " + count[0] + " times, expected 2");
        }
        for (int i = 0; i < expected.length; i++) {
            if (got[i] != expected[i]) {
                throw new RuntimeException("Value mismatch at index " + i);
            }
        }
    }

    static void testEmptyStreamReturnsFalseWithoutAccept() {
        Spliterator.OfInt sp = new Random(1L).ints(0L).spliterator();
        int[] count = new int[1];
        IntConsumer consumer = v -> count[0]++;
        if (sp.tryAdvance(consumer)) {
            throw new RuntimeException("tryAdvance on empty spliterator should return false");
        }
        if (count[0] != 0) {
            throw new RuntimeException("Consumer should not be invoked for empty spliterator");
        }
    }
}
