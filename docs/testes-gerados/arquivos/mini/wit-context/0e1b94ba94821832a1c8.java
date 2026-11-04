/*
 * @test
 * @summary Test java.util.ArrayList.toArray() for fail-fast and boundary conditions
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Arrays;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayConcurrentModification();
        testToArrayIllegalArgument();
    }

    private static void testToArrayNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Object[] arr = list.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected array length 3, got " + arr.length);
        }
        if (!"a".equals(arr[0]) || !"b".equals(arr[1]) || !"c".equals(arr[2])) {
            throw new AssertionError("Array contents do not match list elements");
        }
    }

    private static void testToArrayConcurrentModification() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        // Create a subList to simulate the root and modCount scenario
        java.util.List<String> subList = list.subList(0, 2);

        // Modify the root list structurally to cause modCount mismatch
        list.add("z");

        try {
            // The subList.toArray() should throw ConcurrentModificationException
            subList.toArray();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }

    private static void testToArrayIllegalArgument() {
        // The IllegalArgumentException path is related to invalid range in copyOfRange
        // This can be triggered by creating a subList with invalid indices
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        try {
            // Create a subList with invalid range to simulate offset + size - offset < 0
            // This is not possible via public API directly, so we test that no exception is thrown
            // by normal subList and toArray calls.
            java.util.List<String> subList = list.subList(1, 1); // empty subList
            Object[] arr = subList.toArray();
            if (arr.length != 0) {
                throw new AssertionError("Expected empty array from empty subList");
            }
        } catch (IllegalArgumentException iae) {
            throw new AssertionError("IllegalArgumentException should not be thrown for valid subList");
        }
    }
}
