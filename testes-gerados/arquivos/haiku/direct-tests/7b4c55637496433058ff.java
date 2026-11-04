/*
 * @test
 * @summary Verifies Spliterators.IntIteratorSpliterator.forEachRemaining(IntConsumer) null check and delegation to the wrapped PrimitiveIterator.OfInt
 * @run main SpliteratorsWitupTest
 */

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    static PrimitiveIterator.OfInt iter(final int... values) {
        return new PrimitiveIterator.OfInt() {
            int i = 0;
            public boolean hasNext() { return i < values.length; }
            public int nextInt() { return values[i++]; }
        };
    }

    static void check(boolean cond, String msg) {
        if (!cond) throw new RuntimeException(msg);
    }

    public static void main(String[] args) {
        // Full traversal delegates to the wrapped iterator
        Spliterator.OfInt s = Spliterators.spliteratorUnknownSize(iter(1, 2, 3), 0);
        final int[] sum = {0};
        final int[] count = {0};
        IntConsumer collector = x -> { sum[0] += x; count[0]++; };
        s.forEachRemaining(collector);
        check(sum[0] == 6, "sum expected 6 but was " + sum[0]);
        check(count[0] == 3, "count expected 3 but was " + count[0]);

        // After exhaustion, forEachRemaining must not invoke the action again
        s.forEachRemaining(collector);
        check(count[0] == 3, "action invoked on exhausted spliterator");

        // Partial consumption: only remaining elements are visited
        Spliterator.OfInt s2 = Spliterators.spliteratorUnknownSize(iter(1, 2, 3), 0);
        final int[] sum2 = {0};
        IntConsumer first = x -> sum2[0] += x;
        s2.tryAdvance(first);
        check(sum2[0] == 1, "tryAdvance expected 1 but was " + sum2[0]);
        s2.forEachRemaining(first);
        check(sum2[0] == 6, "remaining sum expected 6 but was " + sum2[0]);

        // Null action must raise NullPointerException
        Spliterator.OfInt s3 = Spliterators.spliteratorUnknownSize(iter(1), 0);
        boolean npe = false;
        try {
            s3.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException for null action");
    }
}
