/*
 * @test
 * @summary Test getComparator method of java.util.Spliterators
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Comparator;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testGetComparatorWithSorted();
        testGetComparatorWithoutSorted();
    }

    static void testGetComparatorWithSorted() {
        Spliterator<Double> spliterator = new DummySpliterator(true);
        try {
            Comparator<? super Double> comparator = java.util.Spliterators.getComparator(spliterator);
            assert comparator == null : "Expected null comparator for sorted spliterator";
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException for sorted spliterator", e);
        }
    }

    static void testGetComparatorWithoutSorted() {
        Spliterator<Double> spliterator = new DummySpliterator(false);
        try {
            java.util.Spliterators.getComparator(spliterator);
            throw new AssertionError("Expected IllegalStateException for non-sorted spliterator");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static class DummySpliterator implements Spliterator<Double> {
        private final boolean sorted;

        DummySpliterator(boolean sorted) {
            this.sorted = sorted;
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
            return false;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public void forEachRemaining(java.util.function.Consumer<? super Double> action) {
            // no-op
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super Double> action) {
            return false;
        }

        @Override
        public Spliterator<Double> trySplit() {
            return null;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if (sorted) {
                return null;
            }
            throw new IllegalStateException();
        }
    }
}
