/*
 * @test
 * @summary Test Spliterators.IteratorSpliterator.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestIteratorSpliterator<T> extends Spliterators.IteratorSpliterator<T> {
        private final int characteristics;

        TestIteratorSpliterator(int characteristics) {
            super(null, 0, characteristics); // null iterator, size 0, characteristics
            this.characteristics = characteristics;
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
            return (this.characteristics & characteristics) == characteristics;
        }
    }

    public static void main(String[] args) {
        // Case 1: hasCharacteristics(SORTED) == true -> getComparator() returns null
        TestIteratorSpliterator<Object> sortedSpliterator = new TestIteratorSpliterator<>(Spliterator.SORTED);
        Comparator<? super Object> comp = sortedSpliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator when SORTED characteristic is present");
        }

        // Case 2: hasCharacteristics(SORTED) == false -> getComparator() throws IllegalStateException
        TestIteratorSpliterator<Object> unsortedSpliterator = new TestIteratorSpliterator<>(0);
        boolean threw = false;
        try {
            unsortedSpliterator.getComparator();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when SORTED characteristic is absent");
        }
    }
}
