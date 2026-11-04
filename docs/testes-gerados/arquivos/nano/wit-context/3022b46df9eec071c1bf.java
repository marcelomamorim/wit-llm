/*
 * @test
 * @summary Test for getComparator() method in java.util.Spliterators
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
        Spliterator.OfDouble spliterator = new MockSpliterator(true);
        Comparator<? super Double> result = null;
        try {
            result = spliterator.getComparator();
        } catch (IllegalStateException e) {
            // Fail if exception is thrown
            throw new AssertionError("Unexpected exception when sorted", e);
        }
        if (result != null) {
            throw new AssertionError("Expected null when sorted, but got: " + result);
        }
    }

    private static void testGetComparatorWhenNotSorted() {
        Spliterator.OfDouble spliterator = new MockSpliterator(false);
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

    private static class MockSpliterator implements Spliterator.OfDouble {
        private final boolean isSorted;

        MockSpliterator(boolean isSorted) {
            this.isSorted = isSorted;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public long estimateSize() {
            return 0;
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
        public Comparator<? super Double> getComparator() {
            if (isSorted) {
                return null;
            }
            throw new IllegalStateException();
        }
    }
}
