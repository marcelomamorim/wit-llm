/*
 * @test
 * @summary Test java.util.ArrayList.sort(Comparator) for basic correctness and fail-fast behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSortWithNaturalOrder();
        testSortWithNullComparator();
        testSortFailFastOnConcurrentModification();
    }

    private static void testSortWithNaturalOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort(null); // natural order
        if (list.size() != 3) {
            throw new AssertionError("Size changed after sort");
        }
        if (!(list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 3)) {
            throw new AssertionError("List not sorted in natural order");
        }
    }

    private static void testSortWithNullComparator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("b");
        list.add("a");
        list.add("c");
        list.sort(null); // natural order
        if (list.size() != 3) {
            throw new AssertionError("Size changed after sort with null comparator");
        }
        if (!("a".equals(list.get(0)) && "b".equals(list.get(1)) && "c".equals(list.get(2)))) {
            throw new AssertionError("List not sorted in natural order with null comparator");
        }
    }

    private static void testSortFailFastOnConcurrentModification() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // We simulate concurrent modification by modifying the list during sort
        // Since we cannot directly modify during sort, we simulate by subclassing
        ArrayList<Integer> modList = new ArrayList<Integer>() {
            @Override
            public void sort(Comparator<? super Integer> c) {
                // Modify list before calling super.sort to trigger ConcurrentModificationException
                this.add(4);
                super.sort(c);
            }
        };
        modList.addAll(list);

        boolean caught = false;
        try {
            modList.sort(null);
        } catch (ConcurrentModificationException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        }
    }
}
