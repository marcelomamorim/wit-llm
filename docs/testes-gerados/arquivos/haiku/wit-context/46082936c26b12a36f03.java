/*
 * @test
 * @summary Checks Random int stream spliterator forEachRemaining null handling, empty streams and deterministic output
 * @run main RandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNpe() {
        Spliterator.OfInt sp = new Random(42L).ints(3).spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testEmptyStreamNoCalls() {
        Spliterator.OfInt sp = new Random(7L).ints(0).spliterator();
        final int[] calls = {0};
        sp.forEachRemaining((IntConsumer) x -> calls[0]++);
        check(calls[0] == 0, "empty stream must not invoke consumer");
    }

    static void testDeterministicValues() {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        new Random(12345L).ints(5).spliterator().forEachRemaining((IntConsumer) first::add);
        new Random(12345L).ints(5).spliterator().forEachRemaining((IntConsumer) second::add);
        check(first.size() == 5, "expected 5 elements, got " + first.size());
        check(first.equals(second), "same seed must yield same sequence");
    }

    static void testMatchesIteratorOrder() {
        final List<Integer> viaForEach = new ArrayList<>();
        new Random(99L).ints(4).spliterator().forEachRemaining((IntConsumer) viaForEach::add);
        int[] viaToArray = new Random(99L).ints(4).toArray();
        check(viaForEach.size() == viaToArray.length, "length mismatch");
        for (int i = 0; i < viaToArray.length; i++) {
            check(viaForEach.get(i) == viaToArray[i], "mismatch at index " + i);
        }
    }

    public static void main(String[] args) {
        testNullConsumerThrowsNpe();
        testEmptyStreamNoCalls();
        testDeterministicValues();
        testMatchesIteratorOrder();
    }
}
