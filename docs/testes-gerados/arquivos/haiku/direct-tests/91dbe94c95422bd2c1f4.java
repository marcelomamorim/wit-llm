/*
 * @test
 * @summary Tests ThreadLocalRandom int spliterator forEachRemaining: range, count, exhaustion and null consumer
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testRangeAndCount();
        testSecondTraversalIsEmpty();
        testNullConsumerThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRangeAndCount() {
        final int n = 100;
        final int[] count = {0};
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(n, 5, 10).spliterator();
        sp.forEachRemaining((IntConsumer) v -> {
            count[0]++;
            check(v >= 5 && v < 10, "value out of range: " + v);
        });
        check(count[0] == n, "expected " + n + " elements but got " + count[0]);
    }

    static void testSecondTraversalIsEmpty() {
        final int n = 20;
        final int[] count = {0};
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(n, 0, 3).spliterator();
        IntConsumer counter = v -> {
            count[0]++;
            check(v >= 0 && v < 3, "value out of range: " + v);
        };
        sp.forEachRemaining(counter);
        check(count[0] == n, "first traversal expected " + n + " but got " + count[0]);
        sp.forEachRemaining(counter);
        check(count[0] == n, "second traversal should be empty but count is " + count[0]);
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(5, 0, 10).spliterator();
        try {
            sp.forEachRemaining((IntConsumer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
