/*
 * @test
 * @summary Behavioural tests for Spliterators array-backed IntSpliterator forEachRemaining (public API observable behaviour)
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingVisitsAllInOrder();
        testNullActionThrowsNPE();
        testPartialConsumptionThenForEachRemaining();
        testSubrangeForEachRemaining();
        testEmptyArrayForEachRemaining();
        testExhaustedSpliteratorDoesNotVisit();
    }

    static void testForEachRemainingVisitsAllInOrder() {
        int[] data = {1, 2, 3, 4, 5};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) v -> seen.add(v));
        check(seen.equals(Arrays.asList(1, 2, 3, 4, 5)), "all elements visited in order");
    }

    static void testNullActionThrowsNPE() {
        int[] data = {1, 2, 3};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            sp.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action throws NullPointerException");
    }

    static void testPartialConsumptionThenForEachRemaining() {
        int[] data = {1, 2, 3, 4, 5};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        boolean advanced = sp.tryAdvance((IntConsumer) v -> { });
        check(advanced, "tryAdvance consumes first element");
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) v -> seen.add(v));
        check(seen.equals(Arrays.asList(2, 3, 4, 5)), "forEachRemaining visits only remaining elements");
    }

    static void testSubrangeForEachRemaining() {
        int[] data = {1, 2, 3, 4, 5};
        Spliterator.OfInt sp = Spliterators.spliterator(data, 1, 4, Spliterator.ORDERED);
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) v -> seen.add(v));
        check(seen.equals(Arrays.asList(2, 3, 4)), "subrange [1,4) visited");
    }

    static void testEmptyArrayForEachRemaining() {
        int[] data = new int[0];
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) v -> seen.add(v));
        check(seen.isEmpty(), "empty array yields no elements");
    }

    static void testExhaustedSpliteratorDoesNotVisit() {
        int[] data = {7, 8};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        sp.forEachRemaining((IntConsumer) v -> { });
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining((IntConsumer) v -> seen.add(v));
        check(seen.isEmpty(), "second forEachRemaining visits nothing");
        check(!sp.tryAdvance((IntConsumer) v -> { }), "tryAdvance false after exhaustion");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Failed: " + message);
        }
    }
}
