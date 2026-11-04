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
        class DummySpliterator implements Spliterator<Object> {
            @Override
            public boolean hasCharacteristics(int characteristics) {
                return (characteristics & Spliterator.SORTED) != 0;
            }
            @Override
            public int characteristics() {
                return Spliterator.SORTED;
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
            public void forEachRemaining(java.util.function.Consumer<? super Object> action) {
            }
        }
        Spliterator<Object> spliterator = new DummySpliterator();
        Comparator<? super Object> result = getComparator(spliterator);
        if (result != null) {
            System.out.println("testGetComparatorWhenSorted: PASSED");
        } else {
            System.out.println("testGetComparatorWhenSorted: FAILED");
        }
    }

    static void testGetComparatorWhenNotSorted() {
        class DummySpliterator implements Spliterator<Object> {
            @Override
            public boolean hasCharacteristics(int characteristics) {
                return false;
            }
            @Override
            public int characteristics() {
                return 0;
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
            public void forEachRemaining(java.util.function.Consumer<? super Object> action) {
            }
        }
        Spliterator<Object> spliterator = new DummySpliterator();
        try {
            getComparator(spliterator);
            System.out.println("testGetComparatorWhenNotSorted: FAILED");
        } catch (IllegalStateException e) {
            System.out.println("testGetComparatorWhenNotSorted: PASSED");
        }
    }

    static <T> Comparator<? super T> getComparator(Spliterator<T> spliterator) {
        return java.util.Spliterators.getComparator(spliterator);
    }
}
