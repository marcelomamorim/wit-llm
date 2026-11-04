/*
 * @test
 * @summary Checks Random.ints(...).spliterator().forEachRemaining(IntConsumer) through public API
 * @run main RandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSequenceMatchesNextInt() {
        Random a = new Random(42L);
        Random b = new Random(42L);
        List<Integer> got = new ArrayList<>();
        Spliterator.OfInt spl = a.ints(5L, 5, 15).spliterator();
        spl.forEachRemaining((IntConsumer) v -> got.add(v));
        check(got.size() == 5, "expected 5 elements, got " + got.size());
        for (int i = 0; i < 5; i++) {
            int expected = b.nextInt(10) + 5;
            check(got.get(i) == expected,
                  "index " + i + ": expected " + expected + " got " + got.get(i));
        }
    }

    static void testSecondCallProducesNothing() {
        Random a = new Random(7L);
        List<Integer> got = new ArrayList<>();
        Spliterator.OfInt spl = a.ints(3L, 0, 100).spliterator();
        spl.forEachRemaining((IntConsumer) v -> got.add(v));
        spl.forEachRemaining((IntConsumer) v -> got.add(v));
        check(got.size() == 3, "expected 3 elements after two calls, got " + got.size());
    }

    static void testEmptyStream() {
        Random a = new Random(1L);
        int[] calls = new int[1];
        Spliterator.OfInt spl = a.ints(0L, 0, 10).spliterator();
        spl.forEachRemaining((IntConsumer) v -> calls[0]++);
        check(calls[0] == 0, "consumer invoked for empty stream");
    }

    static void testNullConsumerThrowsNPE() {
        Random a = new Random(3L);
        Spliterator.OfInt spl = a.ints(2L, 0, 10).spliterator();
        boolean thrown = false;
        try {
            spl.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null consumer");
    }

    public static void main(String[] args) {
        testSequenceMatchesNextInt();
        testSecondCallProducesNothing();
        testEmptyStream();
        testNullConsumerThrowsNPE();
    }
}
