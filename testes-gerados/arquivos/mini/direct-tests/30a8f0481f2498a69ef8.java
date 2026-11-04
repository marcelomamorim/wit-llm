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
        testGetComparatorWithSortedCharacteristic();
        testGetComparatorWithoutSortedCharacteristic();
    }

    private static void testGetComparatorWithSortedCharacteristic() {
        Spliterator<Integer> spliterator = new Spliterators.AbstractSpliterator<Integer>(1, Spliterator.SORTED) {
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) {
                return false;
            }

            @Override
            public Comparator<? super Integer> getComparator() {
                if (hasCharacteristics(Spliterator.SORTED))
                    return null;
                throw new IllegalStateException();
            }
        };

        Comparator<? super Integer> comp = spliterator.getComparator();
        if (comp != null) {
            throw new AssertionError("Expected null comparator for SORTED characteristic");
        }
    }

    private static void testGetComparatorWithoutSortedCharacteristic() {
        Spliterator<Integer> spliterator = new Spliterators.AbstractSpliterator<Integer>(1, 0) {
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) {
                return false;
            }

            @Override
            public Comparator<? super Integer> getComparator() {
                if (hasCharacteristics(Spliterator.SORTED))
                    return null;
                throw new IllegalStateException();
            }
        };

        try {
            spliterator.getComparator();
            throw new AssertionError("Expected IllegalStateException when SORTED characteristic is not present");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
