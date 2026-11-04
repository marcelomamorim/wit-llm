/*
 * @test
 * @summary Test java.util.ArrayList remove(int index) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveFirstElement();
        testRemoveLastElement();
        testRemoveEmptyListThrows();
        testRemoveIndexOutOfBoundsNegative();
        testRemoveIndexOutOfBoundsTooLarge();
    }

    private static void testRemoveValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element 'b', got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2 after removal, got: " + list.size());
        }
        if (!"a".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new AssertionError("List elements incorrect after removal");
        }
    }

    private static void testRemoveFirstElement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        int removed = list.remove(0);
        if (removed != 10) {
            throw new AssertionError("Expected removed element 10, got: " + removed);
        }
        if (list.size() != 1 || list.get(0) != 20) {
            throw new AssertionError("List state incorrect after removing first element");
        }
    }

    private static void testRemoveLastElement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);
        int removed = list.remove(list.size() - 1);
        if (removed != 200) {
            throw new AssertionError("Expected removed element 200, got: " + removed);
        }
        if (list.size() != 1 || list.get(0) != 100) {
            throw new AssertionError("List state incorrect after removing last element");
        }
    }

    private static void testRemoveEmptyListThrows() {
        ArrayList<Object> list = new ArrayList<>();
        try {
            list.remove(0);
            throw new AssertionError("Expected IndexOutOfBoundsException for remove on empty list");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testRemoveIndexOutOfBoundsNegative() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        try {
            list.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testRemoveIndexOutOfBoundsTooLarge() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        try {
            list.remove(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
