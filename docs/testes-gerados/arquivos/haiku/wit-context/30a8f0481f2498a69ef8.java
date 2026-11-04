/*
 * @test
 * @summary Verify getComparator behavior of spliterators created by Spliterators.spliterator(Object[], int)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        Object[] data = {"a", "b", "c"};

        Spliterator<Object> unsorted = Spliterators.spliterator(data, 0);
        Spliterator<Object> sorted = Spliterators.spliterator(data, Spliterator.SORTED);

        if (unsorted.hasCharacteristics(Spliterator.SORTED)) {
            throw new RuntimeException("unsorted spliterator must not report SORTED");
        }
        if (!sorted.hasCharacteristics(Spliterator.SORTED)) {
            throw new RuntimeException("sorted spliterator must report SORTED");
        }

        testUnsortedThrowsIllegalStateException(unsorted);
        testSortedReturnsNullComparator(sorted);
    }

    private static void testUnsortedThrowsIllegalStateException(Spliterator<Object> s) {
        boolean thrown = false;
        try {
            s.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getComparator() without SORTED must throw IllegalStateException");
        }
    }

    private static void testSortedReturnsNullComparator(Spliterator<Object> s) {
        if (s.getComparator() != null) {
            throw new RuntimeException("getComparator() with SORTED must return null");
        }
    }
}
