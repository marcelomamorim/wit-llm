/*
 * @test
 * @summary Functional tests for ArrayList.add(int, E) based on the public observable behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAtStart();
        testAddInMiddle();
        testAddAtEnd();
        testAddNullElement();
        testAddIntoZeroCapacityList();
        testGrowthPreservesOrder();
        testNegativeIndexThrows();
        testIndexGreaterThanSizeThrows();
        testFailedAddDoesNotModifyList();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testAddAtStart() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "a");
        list.add(0, "b");
        check(list.size() == 2, "size should be 2");
        check("b".equals(list.get(0)), "index 0 should be b");
        check("a".equals(list.get(1)), "index 1 should be a");
    }

    static void testAddInMiddle() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("z");
        list.add(1, "y");
        check(list.size() == 3, "size should be 3");
        check("x".equals(list.get(0)), "index 0 should be x");
        check("y".equals(list.get(1)), "index 1 should be y");
        check("z".equals(list.get(2)), "index 2 should be z");
    }

    static void testAddAtEnd() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(1, "b");
        check(list.size() == 2, "size should be 2");
        check("a".equals(list.get(0)), "index 0 should be a");
        check("b".equals(list.get(1)), "index 1 should be b");
    }

    static void testAddNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(0, null);
        check(list.size() == 2, "size should be 2");
        check(list.get(0) == null, "index 0 should be null");
        check("a".equals(list.get(1)), "index 1 should be a");
    }

    static void testAddIntoZeroCapacityList() {
        ArrayList<String> list = new ArrayList<>(0);
        list.add(0, "only");
        check(list.size() == 1, "size should be 1");
        check("only".equals(list.get(0)), "index 0 should be only");
    }

    static void testGrowthPreservesOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        int n = 100;
        for (int i = 0; i < n; i++) {
            list.add(0, i);
        }
        check(list.size() == n, "size should be " + n);
        for (int i = 0; i < n; i++) {
            check(list.get(i) == n - 1 - i, "unexpected element at index " + i);
        }
    }

    static void testNegativeIndexThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(-1, "b");
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for index -1");
    }

    static void testIndexGreaterThanSizeThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(2, "b");
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for index 2 with size 1");
    }

    static void testFailedAddDoesNotModifyList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(5, "b");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        check(list.size() == 1, "size should remain 1 after failed add");
        check("a".equals(list.get(0)), "element should remain a after failed add");
    }
}
