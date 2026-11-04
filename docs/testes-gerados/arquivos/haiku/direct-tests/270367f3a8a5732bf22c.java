/*
 * @test
 * @summary Verifies Spliterators.OfLong tryAdvance(LongConsumer) behavior for null action, empty and non-empty iterators
 * @run main SpliteratorsWitupTest
 */
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    static PrimitiveIterator.OfLong iter(final long... values) {
        return new PrimitiveIterator.OfLong() {
            int i = 0;
            public boolean hasNext() { return i < values.length; }
            public long nextLong() { return values[i++]; }
        };
    }

    static void check(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    static void testNullActionWithElements() {
        Spliterator.OfLong s = Spliterators.spliterator(iter(1L), 1, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testNullActionEmpty() {
        Spliterator.OfLong s = Spliterators.spliterator(iter(), 0, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException even when empty");
    }

    static void testEmptyReturnsFalseWithoutAccept() {
        Spliterator.OfLong s = Spliterators.spliterator(iter(), 0, Spliterator.ORDERED);
        long[] calls = new long[1];
        LongConsumer action = v -> calls[0]++;
        check(!s.tryAdvance(action), "empty iterator must return false");
        check(calls[0] == 0, "action must not be invoked for empty iterator");
    }

    static void testSingleElement() {
        Spliterator.OfLong s = Spliterators.spliterator(iter(42L), 1, Spliterator.ORDERED);
        long[] got = new long[1];
        int[] calls = new int[1];
        LongConsumer action = v -> { got[0] = v; calls[0]++; };
        check(s.tryAdvance(action), "first tryAdvance must return true");
        check(calls[0] == 1, "action must be invoked exactly once");
        check(got[0] == 42L, "action must receive element 42");
        check(!s.tryAdvance(action), "second tryAdvance must return false");
        check(calls[0] == 1, "action must not be invoked after exhaustion");
    }

    static void testSequenceInOrder() {
        Spliterator.OfLong s = Spliterators.spliterator(iter(5L, 6L, 7L), 3, Spliterator.ORDERED);
        long[] got = new long[3];
        int[] idx = new int[1];
        LongConsumer action = v -> got[idx[0]++] = v;
        check(s.tryAdvance(action), "advance 1 must return true");
        check(s.tryAdvance(action), "advance 2 must return true");
        check(s.tryAdvance(action), "advance 3 must return true");
        check(!s.tryAdvance(action), "advance 4 must return false");
        check(idx[0] == 3, "exactly three elements must be consumed");
        check(got[0] == 5L && got[1] == 6L && got[2] == 7L, "elements must be delivered in order");
    }

    public static void main(String[] args) {
        testNullActionWithElements();
        testNullActionEmpty();
        testEmptyReturnsFalseWithoutAccept();
        testSingleElement();
        testSequenceInOrder();
    }
}
