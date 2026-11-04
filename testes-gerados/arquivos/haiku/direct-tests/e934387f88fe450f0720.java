/*
 * @test
 * @summary Verifies Spliterator.OfLong.getComparator() returns null when SORTED is reported and throws IllegalStateException otherwise
 * @run main SpliteratorsWitupTest
 */

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.LongStream;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testSortedCharacteristicReturnsNullComparator();
        testUnsortedCharacteristicThrowsIllegalStateException();
    }

    static void testSortedCharacteristicReturnsNullComparator() {
        PrimitiveIterator.OfLong it = LongStream.of(3L, 1L, 2L).iterator();
        Spliterator.OfLong s = Spliterators.spliterator(it, 3L,
                Spliterator.ORDERED | Spliterator.SORTED);
        Object cmp = s.getComparator();
        if (cmp != null) {
            throw new RuntimeException("Expected null comparator for SORTED spliterator, got: " + cmp);
        }
    }

    static void testUnsortedCharacteristicThrowsIllegalStateException() {
        PrimitiveIterator.OfLong it = LongStream.of(3L, 1L, 2L).iterator();
        Spliterator.OfLong s = Spliterators.spliterator(it, 3L, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException for non-SORTED spliterator");
        }
    }
}
