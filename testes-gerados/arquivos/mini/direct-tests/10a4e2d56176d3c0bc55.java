/*
 * @test
 * @summary Test CopyOnWriteArrayList.toArray() returns correct snapshot array
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        // Initially empty list
        Object[] arrEmpty = list.toArray();
        if (arrEmpty.length != 0) {
            throw new AssertionError("Expected empty array for empty list");
        }

        // Add elements and verify toArray returns them in order
        list.add("a");
        list.add("b");
        list.add(null);
        list.add("d");

        Object[] arr = list.toArray();
        if (arr.length != 4) {
            throw new AssertionError("Expected array length 4, got " + arr.length);
        }
        if (!"a".equals(arr[0])) {
            throw new AssertionError("Expected 'a' at index 0");
        }
        if (!"b".equals(arr[1])) {
            throw new AssertionError("Expected 'b' at index 1");
        }
        if (arr[2] != null) {
            throw new AssertionError("Expected null at index 2");
        }
        if (!"d".equals(arr[3])) {
            throw new AssertionError("Expected 'd' at index 3");
        }

        // Verify that modifying the list after toArray does not affect the returned array
        Object[] snapshot = list.toArray();
        list.add("e");
        if (snapshot.length != 4) {
            throw new AssertionError("Snapshot array length changed after list modification");
        }
        if (list.size() != 5) {
            throw new AssertionError("List size should be 5 after adding element");
        }

        // Verify that toArray returns a new array each time
        Object[] arr1 = list.toArray();
        Object[] arr2 = list.toArray();
        if (arr1 == arr2) {
            throw new AssertionError("toArray() should return a new array instance each call");
        }

        // Verify contents equality
        if (!Arrays.equals(arr1, arr2)) {
            throw new AssertionError("Arrays returned by toArray() calls should be equal");
        }
    }
}
