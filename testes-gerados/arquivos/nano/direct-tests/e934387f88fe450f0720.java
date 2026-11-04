/*
 * @test
 * @summary Test getComparator() method of java.util.Spliterators
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
        class DummySpliterator implements Spliterator<Long> {
            @Override
            public boolean hasCharacteristics(int characteristics) {
                return (characteristics & Spliterator.SORTED) != 0;
            }
            @Override
            public int characteristics() {
                return Spliterator.SORTED;
            }
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Long> action) {
                return false;
            }
            @Override
            public java.util.Spliterator<Long> trySplit() {
                return null;
            }
            @Override
            public long estimateSize() {
                return 0;
            }
            @Override
            public void forEachRemaining(java.util.function.Consumer<? super Long> action) {
            }
        }
        Spliterator<Long> spliterator = new DummySpliterator();
        Comparator<? super Long> result = java.util.Spliterators.getComparator(spliterator);
        if (result != null) {
            System.out.println("testGetComparatorWhenSorted: PASSED");
        } else {
            System.out.println("testGetComparatorWhenSorted: FAILED");
        }
    }

    static void testGetComparatorWhenNotSorted() {
        class DummySpliterator implements Spliterator<Long> {
            @Override
            public boolean hasCharacteristics(int characteristics) {
                return false;
            }
            @Override
            public int characteristics() {
                return 0;
            }
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Long> action) {
                return false;
            }
            @Override
            public java.util.Spliterator<Long> trySplit() {
                return null;
            }
            @Override
            public long estimateSize() {
                return 0;
            }
            @Override
            public void forEachRemaining(java.util.function.Consumer<? super Long> action) {
            }
        }
        Spliterator<Long> spliterator = new DummySpliterator();
        boolean threwException = false;
        try {
            java.util.Spliterators.getComparator(spliterator);
        } catch (IllegalStateException e) {
            threwException = true;
        }
        if (threwException) {
            System.out.println("testGetComparatorWhenNotSorted: PASSED");
        } else {
            System.out.println("testGetComparatorWhenNotSorted: FAILED");
        }
    }
}
