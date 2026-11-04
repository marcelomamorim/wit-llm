/*
 * @test
 * @summary Test java.util.ArrayList.toArray() method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testEmptyListToArray();
        testNonEmptyListToArray();
        testToArrayContentsIndependentCopy();
    }

    private static void testEmptyListToArray() {
        ArrayList<Object> list = new ArrayList<>();
        Object[] arr = list.toArray();
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array for empty list, got length " + arr.length);
        }
    }

    private static void testNonEmptyListToArray() {
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

    private static void testToArrayContentsIndependentCopy() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        Object[] arr = list.toArray();
        arr[0] = "y";
        Object[] arr2 = list.toArray();
        if (!"x".equals(arr2[0])) {
            throw new AssertionError("Modifying returned array should not affect list contents");
        }
    }
}
