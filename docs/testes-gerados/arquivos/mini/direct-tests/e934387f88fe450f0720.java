/*
 * @test
 * @summary Test Spliterators.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractSpliterator<Long> {
        private final int characteristics;

        TestSpliterator(int characteristics) {
            super(1, characteristics);
            this.characteristics = characteristics;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super Long> action) {
            return false;
        }

        @Override
        public Comparator<? super Long> getComparator() {
            if ((characteristics & Spliterator.SORTED) != 0)
                return null;
            throw new IllegalStateException();
        }
    }

    public static void main(String[] args) {
        // Case 1: Spliterator with SORTED characteristic returns null comparator
        TestSpliterator sortedSpliterator = new TestSpliterator(Spliterator.SORTED);
        Comparator<? super Long> comp = sortedSpliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator for SORTED spliterator");
        }

        // Case 2: Spliterator without SORTED characteristic throws IllegalStateException
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
