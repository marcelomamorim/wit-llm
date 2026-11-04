/*
 * @test
 * @summary Verifies Spliterators.LongArraySpliterator.tryAdvance via public Spliterators.spliterator factories
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNpe() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[]{1L}, 0);
        LongConsumer none = null;
        boolean thrown = false;
        try {
            s.tryAdvance(none);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testEmptyArrayReturnsFalse() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[0], 0);
        int[] calls = {0};
        LongConsumer c = v -> calls[0]++;
        check(!s.tryAdvance(c), "empty array must return false");
        check(calls[0] == 0, "action must not be invoked for empty array");
    }

    static void testSequentialAdvance() {
        long[] data = {10L, 20L, 30L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        long[] seen = new long[3];
        int[] idx = {0};
        LongConsumer c = v -> seen[idx[0]++] = v;
        check(s.tryAdvance(c), "first advance must return true");
        check(s.tryAdvance(c), "second advance must return true");
        check(s.tryAdvance(c), "third advance must return true");
        check(!s.tryAdvance(c), "advance past end must return false");
        check(seen[0] == 10L && seen[1] == 20L && seen[2] == 30L,
              "elements must be delivered in order");
    }

    static void testExhaustedDoesNotInvokeAction() {
        Spliterator.OfLong s = Spliterators.spliterator(new long[]{7L}, 0);
        int[] calls = {0};
        LongConsumer c = v -> calls[0]++;
        check(s.tryAdvance(c), "single element must be consumed");
        check(calls[0] == 1, "action must be called once");
        check(!s.tryAdvance(c), "exhausted spliterator must return false");
        check(calls[0] == 1, "action must not be called after exhaustion");
    }

    static void testSubrangeAdvance() {
        long[] data = {1L, 2L, 3L, 4L, 5L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 1, 4, 0);
        long[] seen = new long[3];
        int[] idx = {0};
        LongConsumer c = v -> seen[idx[0]++] = v;
        int count = 0;
        while (s.tryAdvance(c)) {
            count++;
        }
        check(count == 3, "subrange [1,4) must yield 3 elements, got " + count);
        check(seen[0] == 2L && seen[1] == 3L && seen[2] == 4L,
              "subrange elements must be 2,3,4");
    }

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyArrayReturnsFalse();
        testSequentialAdvance();
        testExhaustedDoesNotInvokeAction();
        testSubrangeAdvance();
    }
}
