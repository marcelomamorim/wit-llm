/*
 * @test
 * @summary Test java.util.Spliterators.LongIteratorSpliterator.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class LongIteratorSpliterator extends Spliterators.LongIteratorSpliterator {
        private final int characteristics;

        LongIteratorSpliterator(int characteristics) {
            super(null, 0, characteristics); // null iterator, size 0, characteristics
            this.characteristics = characteristics;
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
            return (this.characteristics & characteristics) == characteristics;
        }
    }

    public static void main(String[] args) {
        testGetComparatorWithSortedCharacteristic();
        testGetComparatorWithoutSortedCharacteristic();
    }

    private static void testGetComparatorWithSortedCharacteristic() {
        LongIteratorSpliterator spliterator = new LongIteratorSpliterator(Spliterator.SORTED);
        Comparator<? super Long> comp = spliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator when SORTED characteristic is present");
        }
    }

    private static void testGetComparatorWithoutSortedCharacteristic() {
        LongIteratorSpliterator spliterator = new LongIteratorSpliterator(0); // no SORTED characteristic
        try {
            spliterator.getComparator();
            throw new AssertionError("Expected IllegalStateException when SORTED characteristic is absent");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
