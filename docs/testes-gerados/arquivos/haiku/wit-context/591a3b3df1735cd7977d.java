/*
 * @test
 * @summary Witup-derived checks for Random.longs() spliterator forEachRemaining(LongConsumer): null consumer, equivalence with toArray, empty range, and exhaustion.
 * @run main RandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.LongConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testMatchesToArray();
        testEmptyStreamNoCalls();
        testExhaustedAfterForEach();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong s = new Random(42L).longs(5, 0L, 100L).spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testMatchesToArray() {
        long[] expected = new Random(7L).longs(10, 5L, 50L).toArray();
        Spliterator.OfLong s = new Random(7L).longs(10, 5L, 50L).spliterator();
        List<Long> actual = new ArrayList<>();
        s.forEachRemaining((LongConsumer) v -> actual.add(v));
        check(actual.size() == expected.length, "size mismatch: " + actual.size());
        for (int i = 0; i < expected.length; i++) {
            check(actual.get(i).longValue() == expected[i],
                  "value mismatch at index " + i);
            check(expected[i] >= 5L && expected[i] < 50L,
                  "value out of bounds at index " + i);
        }
    }

    static void testEmptyStreamNoCalls() {
        final int[] count = {0};
        Spliterator.OfLong s = new Random(3L).longs(0, 0L, 10L).spliterator();
        s.forEachRemaining((LongConsumer) v -> count[0]++);
        check(count[0] == 0, "consumer must not be called for empty stream");
    }

    static void testExhaustedAfterForEach() {
        final int[] count = {0};
        Spliterator.OfLong s = new Random(11L).longs(4, 0L, 1000L).spliterator();
        s.forEachRemaining((LongConsumer) v -> count[0]++);
        check(count[0] == 4, "expected 4 elements, got " + count[0]);
        boolean advanced = s.tryAdvance((LongConsumer) v -> { });
        check(!advanced, "spliterator must be exhausted after forEachRemaining");
        final int[] again = {0};
        s.forEachRemaining((LongConsumer) v -> again[0]++);
        check(again[0] == 0, "second forEachRemaining must not produce elements");
    }
}
