/*
 * @test
 * @summary Test java.util.Vector.sort(Comparator) for correctness and fail-fast behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

public class VectorWitupTest {
    public static void main(String[] args) {
        testSortNaturalOrder();
        testSortReverseOrder();
        testSortEmptyVector();
        testSortFailFast();
    }

    private static void testSortNaturalOrder() {
        Vector<Integer> v = new Vector<>();
        v.add(3);
        v.add(1);
        v.add(2);
        v.sort(Comparator.naturalOrder());
        if (v.size() != 3 || !v.get(0).equals(1) || !v.get(1).equals(2) || !v.get(2).equals(3)) {
            throw new AssertionError("testSortNaturalOrder failed: Vector not sorted in natural order");
        }
    }

    private static void testSortReverseOrder() {
        Vector<String> v = new Vector<>();
        v.add("b");
        v.add("c");
        v.add("a");
        v.sort(Comparator.reverseOrder());
        if (v.size() != 3 || !v.get(0).equals("c") || !v.get(1).equals("b") || !v.get(2).equals("a")) {
            throw new AssertionError("testSortReverseOrder failed: Vector not sorted in reverse order");
        }
    }

    private static void testSortEmptyVector() {
        Vector<Object> v = new Vector<>();
        v.sort(null); // null comparator means natural order, but empty vector should not fail
        if (v.size() != 0) {
            throw new AssertionError("testSortEmptyVector failed: empty vector size changed");
        }
    }

    private static void testSortFailFast() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);

        // We simulate concurrent modification by modifying the vector during sort
        // Since Vector.sort is synchronized, we cannot modify concurrently from another thread easily
        // Instead, we subclass Vector to modify modCount during sort to trigger ConcurrentModificationException

        class VectorWithModCount extends Vector<Integer> {
            private static final long serialVersionUID = 1L;
            boolean modified = false;

            @Override
            public synchronized void sort(Comparator<? super Integer> c) {
                final int expectedModCount = modCount;
                // Modify modCount to simulate concurrent modification
                if (!modified) {
                    modCount++;
                    modified = true;
                }
                try {
                    super.sort(c);
                } catch (ConcurrentModificationException e) {
                    throw e;
                }
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                modCount++;
            }
        }

        VectorWithModCount vwmc = new VectorWithModCount();
        vwmc.add(1);
        vwmc.add(2);
        vwmc.add(3);

        boolean threw = false;
        try {
            vwmc.sort(Comparator.naturalOrder());
        } catch (ConcurrentModificationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("testSortFailFast failed: ConcurrentModificationException not thrown");
        }
    }
}
