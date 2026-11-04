/*
 * @test
 * @summary Witup-generated behavioral tests for Spliterators.LongArraySpliterator.tryAdvance via the public spliterator(long[], int) factory
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testAdvancesElementsInOrderAndReturnsFalseWhenExhausted();
        testEmptyArrayReturnsFalseWithoutInvokingAction();
    }

    static void testNullActionThrowsNPE() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[] {1L}, 0);
        try {
            s.tryAdvance((LongConsumer) null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAdvancesElementsInOrderAndReturnsFalseWhenExhausted() {
        long[] data = {10L, 20L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        long[] seen = new long[1];
        int[] calls = new int[1];
        LongConsumer action = v -> {
            seen[0] = v;
            calls[0]++;
        };

        if (!s.tryAdvance(action)) {
            throw new RuntimeException("First tryAdvance should return true");
        }
        if (seen[0] != 10L || calls[0] != 1) {
            throw new RuntimeException("First element mismatch: " + seen[0]);
        }

        if (!s.tryAdvance(action)) {
            throw new RuntimeException("Second tryAdvance should return true");
        }
        if (seen[0] != 20L || calls[0] != 2) {
            throw new RuntimeException("Second element mismatch: " + seen[0]);
        }

        if (s.tryAdvance(action)) {
            throw new RuntimeException("tryAdvance should return false when exhausted");
        }
        if (calls[0] != 2) {
            throw new RuntimeException("Action must not be invoked when exhausted");
        }
    }

    static void testEmptyArrayReturnsFalseWithoutInvokingAction() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[0], 0);
        int[] calls = new int[1];
        LongConsumer action = v -> calls[0]++;
        if (s.tryAdvance(action)) {
            throw new RuntimeException("Empty array tryAdvance should return false");
        }
        if (calls[0] != 0) {
            throw new RuntimeException("Action must not be invoked for empty array");
        }
    }
}
