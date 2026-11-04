/*
 * @test
 * @summary Verifies forEachRemaining(LongConsumer) on the long array spliterator returned by the public Spliterators factory
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachRemainingVisitsAllElementsInOrder();
        testEmptyArrayDoesNothing();
        testNothingRemainsAfterForEachRemaining();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[]{1L, 2L}, 0);
        try {
            s.forEachRemaining(null);
            check(false, "expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachRemainingVisitsAllElementsInOrder() {
        long[] data = {5L, 7L, 9L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        List<Long> seen = new ArrayList<>();
        LongConsumer collector = v -> seen.add(v);
        s.forEachRemaining(collector);
        check(seen.size() == 3, "expected 3 elements, got " + seen.size());
        check(seen.get(0) == 5L && seen.get(1) == 7L && seen.get(2) == 9L,
              "unexpected order or values: " + seen);
    }

    static void testEmptyArrayDoesNothing() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[0], 0);
        List<Long> seen = new ArrayList<>();
        LongConsumer collector = v -> seen.add(v);
        s.forEachRemaining(collector);
        check(seen.isEmpty(), "expected no elements for empty array");
    }

    static void testNothingRemainsAfterForEachRemaining() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[]{1L, 2L}, 0);
        LongConsumer noop = v -> { };
        s.forEachRemaining(noop);
        check(!s.tryAdvance(noop), "expected no remaining elements after forEachRemaining");
    }
}
