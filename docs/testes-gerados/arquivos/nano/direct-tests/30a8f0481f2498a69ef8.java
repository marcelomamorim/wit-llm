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
        Spliterator<Object> spliterator = new DummySpliterator(true);
        try {
            Comparator<?> comp = getComparator(spliterator);
            if (comp != null) {
                System.out.println("PASS: getComparator() returned non-null for sorted spliterator.");
            } else {
                System.out.println("FAIL: getComparator() returned null for sorted spliterator.");
            }
        } catch (IllegalStateException e) {
            System.out.println("FAIL: Unexpected IllegalStateException for sorted spliterator.");
        }
    }

    static void testGetComparatorWhenNotSorted() {
        Spliterator<Object> spliterator = new DummySpliterator(false);
        try {
            getComparator(spliterator);
            System.out.println("FAIL: Expected IllegalStateException for non-sorted spliterator.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Correctly threw IllegalStateException for non-sorted spliterator.");
        }
    }

    static Comparator<?> getComparator(Spliterator<?> spliterator) {
        return java.util.Spliterators.getComparator();
    }

    static class DummySpliterator implements Spliterator<Object> {
        private final boolean sorted;
        DummySpliterator(boolean sorted) {
            this.sorted = sorted;
        }
        @Override
        public boolean hasCharacteristics(int characteristics) {
            return false;
        }
        @Override
        public long estimateSize() {
            return 0;
        }
        @Override
        public void forEachRemaining(Consumer<? super Object> action) {
        }
        @Override
        public boolean tryAdvance(Consumer<? super Object> action) {
            return false;
        }
        @Override
        public Spliterator<Object> trySplit() {
            return null;
        }
        @Override
        public int characteristics() {
            return 0;
        }
        @Override
        public Comparator<? super Object> getComparator() {
            if (sorted) {
                return (a, b) -> 0; // dummy comparator
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
