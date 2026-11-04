/*
 * @test
 * @summary Verifies Spliterators spliterator getComparator returns null when SORTED and throws IllegalStateException otherwise
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        Object[] data = {"a", "b", "c"};

        Spliterator<Object> unsorted = Spliterators.spliterator(data, Spliterator.ORDERED);
        if (unsorted.hasCharacteristics(Spliterator.SORTED)) {
            throw new AssertionError("unsorted spliterator must not report SORTED");
        }
        boolean threw = false;
        try {
            unsorted.getComparator();
        } catch (IllegalStateException expected) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("expected IllegalStateException from getComparator");
        }

        Spliterator<Object> sorted = Spliterators.spliterator(data, Spliterator.ORDERED | Spliterator.SORTED);
        if (!sorted.hasCharacteristics(Spliterator.SORTED)) {
            throw new AssertionError("sorted spliterator must report SORTED");
        }
        Comparator<? super Object> c = sorted.getComparator();
        if (c != null) {
            throw new AssertionError("expected null comparator for SORTED spliterator");
        }
    }
}
