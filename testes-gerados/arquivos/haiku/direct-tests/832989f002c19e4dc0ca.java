/*
 * @test
 * @summary Verifies Spliterators.OfLong.forEachRemaining(LongConsumer) delegates to the wrapped iterator and rejects a null action
 * @run main SpliteratorsWitupTest
 */

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    static PrimitiveIterator.OfLong makeIt(final long... vals) {
        return new PrimitiveIterator.OfLong() {
            int i = 0;
            public boolean hasNext() { return i < vals.length; }
            public long nextLong() { return vals[i++]; }
        };
    }

    static void check(boolean cond, String msg) {
        if (!cond) throw new RuntimeException(msg);
    }

    static void testDeliversAllElementsInOrder() {
        Spliterator.OfLong sp = Spliterators.spliteratorUnknownSize(
                makeIt(10L, 20L, 30L), Spliterator.ORDERED);
        final long[] seen = new long[3];
        final int[] count = new int[1];
        LongConsumer c = v -> {
            if (count[0] < seen.length) seen[count[0]] = v;
            count[0]++;
        };
        sp.forEachRemaining(c);
        check(count[0] == 3, "expected 3 elements, got " + count[0]);
        check(seen[0] == 10L && seen[1] == 20L && seen[2] == 30L,
                "unexpected element order");
    }

    static void testExhaustedAfterForEachRemaining() {
        Spliterator.OfLong sp = Spliterators.spliteratorUnknownSize(
                makeIt(1L, 2L), Spliterator.ORDERED);
        final int[] count = new int[1];
        LongConsumer c = v -> count[0]++;
        sp.forEachRemaining(c);
        check(count[0] == 2, "expected 2 elements on first pass");
        sp.forEachRemaining(c);
        check(count[0] == 2, "second pass must deliver no elements");
    }

    static void testEmptyIteratorDeliversNothing() {
        Spliterator.OfLong sp = Spliterators.spliteratorUnknownSize(
                makeIt(), Spliterator.ORDERED);
        final int[] count = new int[1];
        LongConsumer c = v -> count[0]++;
        sp.forEachRemaining(c);
        check(count[0] == 0, "empty iterator must not invoke action");
    }

    static void testNullActionThrowsNPE() {
        Spliterator.OfLong sp = Spliterators.spliteratorUnknownSize(
                makeIt(1L), Spliterator.ORDERED);
        boolean thrown = false;
        try {
            sp.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    public static void main(String[] args) {
        testDeliversAllElementsInOrder();
        testExhaustedAfterForEachRemaining();
        testEmptyIteratorDeliversNothing();
        testNullActionThrowsNPE();
    }
}
