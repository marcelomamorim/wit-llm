/* @test    @summary Test getComparator method of java.util.Spliterators    @run main SpliteratorsWitupTest */

import java.util.Spliterator;
import java.util.Comparator;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testGetComparatorWhenSorted();
        testGetComparatorWhenNotSorted();
    }

    static void testGetComparatorWhenSorted() {
        class DummySpliterator implements Spliterator.OfDouble {
            @Override
            public int characteristics() { return Spliterator.SORTED; }
            @Override
            public boolean tryAdvance(java.util.function.DoubleConsumer action) { return false; }
            @Override
            public java.util.Spliterator.OfDouble trySplit() { return null; }
            @Override
            public long estimateSize() { return 0; }
            @Override
            public void forEachRemaining(java.util.function.DoubleConsumer action) {}
        }
        DummySpliterator spliterator = new DummySpliterator();
        java.util.function.Supplier<Comparator<? super Double>> supplier = () -> null;
        try {
            java.util.Comparator<? super Double> result = java.util.Spliterators.getComparator();
            if (result != null) {
                System.out.println("getComparator returned non-null as expected.");
            } else {
                System.out.println("getComparator returned null.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException.");
        }
    }

    static void testGetComparatorWhenNotSorted() {
        class DummySpliterator implements Spliterator.OfDouble {
            @Override
            public int characteristics() { return 0; }
            @Override
            public boolean tryAdvance(java.util.function.DoubleConsumer action) { return false; }
            @Override
            public java.util.Spliterator.OfDouble trySplit() { return null; }
            @Override
            public long estimateSize() { return 0; }
            @Override
            public void forEachRemaining(java.util.function.DoubleConsumer action) {}
        }
        DummySpliterator spliterator = new DummySpliterator();
        try {
            java.util.Comparator<? super Double> result = java.util.Spliterators.getComparator();
            System.out.println("Unexpected success: getComparator returned " + result);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException.");
        }
    }
}
