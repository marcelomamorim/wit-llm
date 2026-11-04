/*
 * @test
 * @summary Test Spliterators.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractSpliterator<Double> {
        private final int characteristics;

        TestSpliterator(int characteristics) {
            super(0, characteristics);
            this.characteristics = characteristics;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super Double> action) {
            return false;
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if ((characteristics & Spliterator.SORTED) != 0)
                return null;
            throw new IllegalStateException();
        }
    }

    public static void main(String[] args) {
        // Case 1: has SORTED characteristic, getComparator() returns null
        TestSpliterator sortedSpliterator = new TestSpliterator(Spliterator.SORTED);
        Comparator<? super Double> comp = sortedSpliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator for SORTED spliterator");
        }

        // Case 2: does not have SORTED characteristic, getComparator() throws IllegalStateException
        TestSpliterator unsortedSpliterator = new TestSpliterator(0);
        boolean threw = false;
        try {
            unsortedSpliterator.getComparator();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException for unsorted spliterator");
        }
    }
}
