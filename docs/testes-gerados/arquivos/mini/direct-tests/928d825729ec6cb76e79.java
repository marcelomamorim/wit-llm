/*
 * @test
 * @summary Test java.util.ArrayList.sort(Comparator) for correctness and fail-fast behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSortNaturalOrder();
        testSortReverseOrder();
        testSortWithNullComparator();
        testSortFailFastOnConcurrentModification();
    }

    private static void testSortNaturalOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort(null); // natural order
        Integer[] expected = {1, 2, 3};
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(list.get(i))) {
                throw new AssertionError("testSortNaturalOrder failed: expected " + Arrays.toString(expected) + " but got " + list);
            }
        }
    }

    private static void testSortReverseOrder() {
        ArrayList<String> list = new ArrayList<>();
        list.add("b");
        list.add("a");
        list.add("c");
        list.sort(Comparator.reverseOrder());
        String[] expected = {"c", "b", "a"};
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(list.get(i))) {
                throw new AssertionError("testSortReverseOrder failed: expected " + Arrays.toString(expected) + " but got " + list);
            }
        }
    }

    private static void testSortWithNullComparator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        // null comparator means natural ordering
        list.sort(null);
        String[] expected = {"x", "y", "z"};
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(list.get(i))) {
                throw new AssertionError("testSortWithNullComparator failed: expected " + Arrays.toString(expected) + " but got " + list);
            }
        }
    }

    private static void testSortFailFastOnConcurrentModification() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // We simulate concurrent modification by modifying modCount via reflection is not allowed,
        // so instead we create a subclass to simulate modCount change during sort.
        // But since modCount is private and no setter, we test fail-fast by modifying list during sort.

        // We create a Comparator that modifies the list during sorting to trigger ConcurrentModificationException
        Comparator<Integer> cmp = new Comparator<Integer>() {
            private boolean modified = false;
            public int compare(Integer a, Integer b) {
                if (!modified) {
                    list.add(4); // structural modification during sort
                    modified = true;
                }
                return a.compareTo(b);
            }
        };

        boolean threw = false;
        try {
            list.sort(cmp);
        } catch (ConcurrentModificationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("testSortFailFastOnConcurrentModification failed: expected ConcurrentModificationException");
        }
    }
}
