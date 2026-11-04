/*
 * @test
 * @summary Test Spliterators.DoubleArraySpliterator.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class DoubleArraySpliterator extends Spliterators.AbstractDoubleSpliterator {
        private final int characteristics;

        DoubleArraySpliterator(int characteristics) {
            super(0, characteristics);
            this.characteristics = characteristics;
        }

        @Override
        public boolean tryAdvance(java.util.function.DoubleConsumer action) {
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
        // Case 1: has SORTED characteristic, getComparator returns null
        DoubleArraySpliterator spliteratorSorted = new DoubleArraySpliterator(Spliterator.SORTED);
        Comparator<? super Double> comp = spliteratorSorted.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator when SORTED characteristic is present");
        }

        // Case 2: does not have SORTED characteristic, getComparator throws IllegalStateException
        DoubleArraySpliterator spliteratorUnsorted = new DoubleArraySpliterator(0);
        boolean threw = false;
        try {
            spliteratorUnsorted.getComparator();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when SORTED characteristic is absent");
        }

        System.out.println("Test passed.");
    }
}
