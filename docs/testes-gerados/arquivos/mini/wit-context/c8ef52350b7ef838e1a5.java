/*
 * @test
 * @summary Test Spliterators.DoubleIteratorSpliterator.getComparator() behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testGetComparatorWithSortedCharacteristic();
        testGetComparatorWithoutSortedCharacteristic();
    }

    private static void testGetComparatorWithSortedCharacteristic() {
        // Create a DoubleIteratorSpliterator with SORTED characteristic
        Spliterator.OfDouble spliterator = new Spliterators.DoubleIteratorSpliterator(new double[0], Spliterator.SORTED) {
            @Override
            public boolean tryAdvance(java.util.function.DoubleConsumer action) {
                return false;
            }
        };

        // When hasCharacteristics(SORTED) is true, getComparator() returns null
        Comparator<? super Double> comp = spliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator when SORTED characteristic is present");
        }
    }

    private static void testGetComparatorWithoutSortedCharacteristic() {
        // Create a DoubleIteratorSpliterator without SORTED characteristic
        Spliterator.OfDouble spliterator = new Spliterators.DoubleIteratorSpliterator(new double[0], 0) {
            @Override
            public boolean tryAdvance(java.util.function.DoubleConsumer action) {
                return false;
            }
        };

        // When hasCharacteristics(SORTED) is false, getComparator() throws IllegalStateException
        try {
            spliterator.getComparator();
            throw new AssertionError("Expected IllegalStateException when SORTED characteristic is absent");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
