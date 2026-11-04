/*
 * @test
 * @summary Test CopyOnWriteArrayList.toArray() method behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testEmptyListToArray();
        testNonEmptyListToArray();
        testToArrayContentIndependence();
    }

    private static void testEmptyListToArray() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        Object[] arr = list.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null for empty list");
        }
        if (arr.length != 0) {
            throw new AssertionError("toArray() length for empty list expected 0 but was " + arr.length);
        }
    }

    private static void testNonEmptyListToArray() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add(null);
        Object[] arr = list.toArray();
        if (arr.length != 3) {
            throw new AssertionError("toArray() length expected 3 but was " + arr.length);
        }
        if (!"a".equals(arr[0])) {
            throw new AssertionError("toArray() element 0 expected 'a' but was " + arr[0]);
        }
        if (!"b".equals(arr[1])) {
            throw new AssertionError("toArray() element 1 expected 'b' but was " + arr[1]);
        }
        if (arr[2] != null) {
            throw new AssertionError("toArray() element 2 expected null but was " + arr[2]);
        }
    }

    private static void testToArrayContentIndependence() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        Object[] arr1 = list.toArray();
        list.add("y");
        Object[] arr2 = list.toArray();
        if (arr1.length != 1 || !"x".equals(arr1[0])) {
            throw new AssertionError("First toArray() snapshot incorrect");
        }
        if (arr2.length != 2 || !"x".equals(arr2[0]) || !"y".equals(arr2[1])) {
            throw new AssertionError("Second toArray() snapshot incorrect");
        }
        // Mutate arr1 and verify it does not affect list or arr2
        arr1[0] = "changed";
        Object[] arr3 = list.toArray();
        if ("changed".equals(arr3[0])) {
            throw new AssertionError("Mutation of toArray() result affected list content");
        }
    }
}
