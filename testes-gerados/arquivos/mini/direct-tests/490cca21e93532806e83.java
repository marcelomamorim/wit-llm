/*
 * @test
 * @summary Test Spliterators.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator<T> implements Spliterator<T> {
        private final int characteristics;

        TestSpliterator(int characteristics) {
            this.characteristics = characteristics;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super T> action) {
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super T> getComparator() {
            if ((characteristics & Spliterator.SORTED) != 0)
                return null;
            throw new IllegalStateException();
        }
    }

    public static void main(String[] args) {
        // Case 1: Spliterator with SORTED characteristic returns null comparator
        TestSpliterator<String> sortedSpliterator = new TestSpliterator<>(Spliterator.SORTED);
        Comparator<? super String> comp = sortedSpliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator for SORTED spliterator");
        }

        // Case 2: Spliterator without SORTED characteristic throws IllegalStateException
        TestSpliterator<String> unsortedSpliterator = new TestSpliterator<>(0);
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
