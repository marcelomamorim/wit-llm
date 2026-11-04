/*
 * @test
 * @summary Behavioral tests for Arrays.asList: null array rejection, list view, write-through and fixed-size semantics
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testEmptyVarargs();
        testListViewOrderAndContent();
        testWriteThroughToBackingArray();
        testFixedSizeRejectsAdd();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.asList((String[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "asList((String[]) null) must throw NullPointerException");
    }

    static void testEmptyVarargs() {
        List<String> list = Arrays.asList();
        check(list.size() == 0, "asList() with no arguments must produce an empty list");
        check(list.isEmpty(), "asList() result must be empty");
    }

    static void testListViewOrderAndContent() {
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);
        check(list.size() == 3, "size must equal array length");
        check("a".equals(list.get(0)), "element 0 mismatch");
        check("b".equals(list.get(1)), "element 1 mismatch");
        check("c".equals(list.get(2)), "element 2 mismatch");
        check(list.contains("c"), "contains must find existing element");
        check(list.indexOf("b") == 1, "indexOf must return position of element");
    }

    static void testWriteThroughToBackingArray() {
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);
        list.set(1, "x");
        check("x".equals(arr[1]), "set on list must write through to backing array");
        arr[2] = "y";
        check("y".equals(list.get(2)), "change in backing array must be visible through list");
    }

    static void testFixedSizeRejectsAdd() {
        String[] arr = {"a", "b"};
        List<String> list = Arrays.asList(arr);
        boolean thrown = false;
        try {
            list.add("z");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "add on asList result must throw UnsupportedOperationException");
        check(list.size() == 2, "failed add must not change list size");
    }
}
