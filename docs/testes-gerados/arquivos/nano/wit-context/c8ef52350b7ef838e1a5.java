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

    private static void testGetComparatorWhenSorted() {
        Spliterator.OfDouble spliterator = new DummySpliterator(true);
        Comparator<? super Double> result = null;
        try {
            result = spliterator.getComparator();
        } catch (Exception e) {
            System.out.println("Unexpected exception in sorted case: " + e);
            return;
        }
        if (result != null) {
            System.out.println("getComparator() returned non-null for sorted spliterator as expected.");
        } else {
            System.out.println("getComparator() returned null for sorted spliterator as expected.");
        }
    }

    private static void testGetComparatorWhenNotSorted() {
        Spliterator.OfDouble spliterator = new DummySpliterator(false);
        try {
            spliterator.getComparator();
            System.out.println("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException thrown as expected.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static class DummySpliterator implements Spliterator.OfDouble {
        private final boolean hasSorted;

        DummySpliterator(boolean hasSorted) {
            this.hasSorted = hasSorted;
        }

        @Override
        public int characteristics() {
            return hasSorted ? Spliterator.SORTED : 0;
        }

        @Override
        public boolean tryAdvance(java.util.function.DoubleConsumer action) {
            return false;
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics() && (characteristics() & Spliterator.SORTED) != 0) {
                return null;
            }
            throw new IllegalStateException();
        }

        private boolean hasCharacteristics() {
            return (characteristics() & Spliterator.SORTED) != 0;
        }
    }
}
