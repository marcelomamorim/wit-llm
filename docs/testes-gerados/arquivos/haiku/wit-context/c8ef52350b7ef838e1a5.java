/*
 * @test
 * @summary Verify getComparator on a Spliterator.OfDouble created by Spliterators.spliterator(PrimitiveIterator.OfDouble, long, int): null when SORTED, IllegalStateException otherwise
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        PrimitiveIterator.OfDouble it = new PrimitiveIterator.OfDouble() {
            public boolean hasNext() { return false; }
            public double nextDouble() { return 0.0; }
        };

        Spliterator.OfDouble sorted = Spliterators.spliterator(it, 0L, Spliterator.SORTED);
        Comparator<? super Double> c = sorted.getComparator();
        if (c != null) {
            throw new RuntimeException("expected null comparator for SORTED spliterator");
        }

        Spliterator.OfDouble ordered = Spliterators.spliterator(it, 0L, Spliterator.ORDERED);
        try {
            ordered.getComparator();
            throw new RuntimeException("expected IllegalStateException for non-SORTED spliterator");
        } catch (IllegalStateException expected) {
        }

        Spliterator.OfDouble none = Spliterators.spliterator(it, 0L, 0);
        try {
            none.getComparator();
            throw new RuntimeException("expected IllegalStateException for spliterator without characteristics");
        } catch (IllegalStateException expected) {
        }
    }
}
