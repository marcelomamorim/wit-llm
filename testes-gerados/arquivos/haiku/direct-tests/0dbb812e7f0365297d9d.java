/*
 * @test
 * @summary Observable behavior of Arrays.asList: fixed-size list view, write-through to the backing array, null handling and null elements
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBasicContentAndOrder();
        testWriteThroughToBackingArray();
        testSetAllowedButAddRemoveRejected();
        testNullArrayThrowsNullPointerException();
        testEmptyVarargsProducesEmptyList();
        testNullElementsArePreserved();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicContentAndOrder() {
        List<String> list = Arrays.asList("a", "b", "c");
        check(list.size() == 3, "size should be 3");
        check("a".equals(list.get(0)), "get(0) should be a");
        check("b".equals(list.get(1)), "get(1) should be b");
        check("c".equals(list.get(2)), "get(2) should be c");
        check(list.indexOf("c") == 2, "indexOf(c) should be 2");
        check(!list.isEmpty(), "list should not be empty");
    }

    static void testWriteThroughToBackingArray() {
        String[] arr = {"x", "y"};
        List<String> list = Arrays.asList(arr);
        list.set(0, "z");
        check("z".equals(arr[0]), "set should write through to backing array");
        arr[1] = "w";
        check("w".equals(list.get(1)), "list should reflect backing array update");
    }

    static void testSetAllowedButAddRemoveRejected() {
        List<String> list = Arrays.asList("p", "q");
        String previous = list.set(1, "r");
        check("q".equals(previous), "set should return previous element");
        check("r".equals(list.get(1)), "set should store new element");

        boolean addRejected = false;
        try {
            list.add("s");
        } catch (UnsupportedOperationException e) {
            addRejected = true;
        }
        check(addRejected, "add should throw UnsupportedOperationException");
        check(list.size() == 2, "size should remain 2 after rejected add");

        boolean removeRejected = false;
        try {
            list.remove(0);
        } catch (UnsupportedOperationException e) {
            removeRejected = true;
        }
        check(removeRejected, "remove should throw UnsupportedOperationException");
        check(list.size() == 2, "size should remain 2 after rejected remove");
    }

    static void testNullArrayThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Arrays.asList((String[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "asList(null array) should throw NullPointerException");
    }

    static void testEmptyVarargsProducesEmptyList() {
        List<String> list = Arrays.asList();
        check(list.size() == 0, "empty varargs should yield size 0");
        check(list.isEmpty(), "empty varargs should yield empty list");
    }

    static void testNullElementsArePreserved() {
        List<String> list = Arrays.asList("a", null);
        check(list.size() == 2, "size should be 2 with a null element");
        check(list.get(1) == null, "null element should be preserved");
        check(list.contains(null), "contains(null) should be true");
    }
}
