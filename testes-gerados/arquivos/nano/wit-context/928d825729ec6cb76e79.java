/*
 * @test
 * @summary Tests for ArrayList.sort method to verify exception paths and behavior.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSortWithNullComparator();
        testSortWithEmptyList();
    }

    private static void testSortWithNullComparator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        try {
            list.sort(null);
            // Expect no exception, list should be sorted
            if (list.get(0) != 1 || list.get(1) != 2 || list.get(2) != 3) {
                throw new AssertionError("List not sorted correctly with null comparator");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception with null comparator", e);
        }
    }

    private static void testSortWithEmptyList() {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            list.sort(Comparator.naturalOrder());
            // Expect no exception, list remains empty
            if (list.size() != 0) {
                throw new AssertionError("Empty list should remain empty after sort");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception on empty list sort", e);
        }
    }
}
