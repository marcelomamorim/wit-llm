/*
 * @test
 * @summary Test java.util.ArrayList remove(int index) method
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
        testRemoveIndexOutOfBoundsLow();
        testRemoveIndexOutOfBoundsHigh();
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

        Integer removed = list.remove(0);
        if (!Integer.valueOf(10).equals(removed)) {
            throw new AssertionError("Expected removed element 10, got: " + removed);
        }
        if (list.size() != 1) {
            throw new AssertionError("Expected size 1 after removal, got: " + list.size());
        }
        if (!Integer.valueOf(20).equals(list.get(0))) {
            throw new AssertionError("List element incorrect after removal");
        }
    }

    private static void testRemoveLastElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        String removed = list.remove(list.size() - 1);
        if (!"y".equals(removed)) {
            throw new AssertionError("Expected removed element 'y', got: " + removed);
        }
        if (list.size() != 1) {
            throw new AssertionError("Expected size 1 after removal, got: " + list.size());
        }
        if (!"x".equals(list.get(0))) {
            throw new AssertionError("List element incorrect after removal");
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

    private static void testRemoveIndexOutOfBoundsLow() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");
        try {
            list.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testRemoveIndexOutOfBoundsHigh() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");
        try {
            list.remove(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
