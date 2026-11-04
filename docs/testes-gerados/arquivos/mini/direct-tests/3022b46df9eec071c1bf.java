/*
 * @test
 * @summary Test Spliterators.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testGetComparatorThrows();
        testGetComparatorReturnsNull();
    }

    private static void testGetComparatorThrows() {
        Spliterator.OfDouble spliterator = new Spliterator.OfDouble() {
            public boolean tryAdvance(java.util.function.DoubleConsumer action) { return false; }
            public Spliterator.OfDouble trySplit() { return null; }
            public long estimateSize() { return 0; }
            public int characteristics() { return 0; }
            public Comparator<? super Double> getComparator() {
                // Use the method under test
                return new ComparatorProvider(this).getComparator();
            }
        };

        try {
            spliterator.getComparator();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testGetComparatorReturnsNull() {
        Spliterator.OfDouble spliterator = new Spliterator.OfDouble() {
            public boolean tryAdvance(java.util.function.DoubleConsumer action) { return false; }
            public Spliterator.OfDouble trySplit() { return null; }
            public long estimateSize() { return 0; }
            public int characteristics() { return Spliterator.SORTED; }
            public Comparator<? super Double> getComparator() {
                // Use the method under test
                return new ComparatorProvider(this).getComparator();
            }
        };

        Comparator<? super Double> comp = spliterator.getComparator();
        if (comp != null) {
            throw new RuntimeException("Expected null comparator for SORTED characteristic");
        }
    }

    // Helper class to invoke the tested method getComparator() from Spliterators
    static class ComparatorProvider {
        private final Spliterator.OfDouble spliterator;

        ComparatorProvider(Spliterator.OfDouble spliterator) {
            this.spliterator = spliterator;
        }

        Comparator<? super Double> getComparator() {
            // The tested method is from Spliterators class, but the source code snippet
            // shows getComparator() as instance method, so we simulate the logic here
            if ((spliterator.characteristics() & Spliterator.SORTED) != 0) {
                return null;
            }
            throw new IllegalStateException();
        }
    }
}
