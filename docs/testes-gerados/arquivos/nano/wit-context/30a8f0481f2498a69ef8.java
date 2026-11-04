/*
 * @test    @summary Test for getComparator() method in java.util.Spliterators
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Comparator;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testGetComparatorWhenSorted();
        testGetComparatorWhenNotSorted();
    }

    static void testGetComparatorWhenSorted() {
        Spliterator<Object> spliterator = new DummySpliterator(true);
        Comparator<?> result = null;
        try {
            result = spliterator.getComparator();
        } catch (IllegalStateException e) {
            // Fail if exception is thrown
            throw new AssertionError("Unexpected exception when sorted", e);
        }
        if (result != null) {
            throw new AssertionError("Expected null comparator for sorted spliterator");
        }
    }

    static void testGetComparatorWhenNotSorted() {
        Spliterator<Object> spliterator = new DummySpliterator(false);
        boolean exceptionThrown = false;
        try {
            spliterator.getComparator();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when not sorted");
        }
    }

    static class DummySpliterator implements Spliterator<Object> {
        private final boolean isSorted;

        DummySpliterator(boolean isSorted) {
            this.isSorted = isSorted;
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
            return (characteristics & Spliterator.SORTED) != 0 && isSorted;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super Object> action) {
            return false;
        }

        @Override
        public java.util.Spliterator<Object> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return isSorted ? Spliterator.SORTED : 0;
        }

        @Override
        public Comparator<? super Object> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }
}
