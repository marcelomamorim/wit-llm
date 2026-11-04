/*
 * @test
 * @summary Test for LinkedList.add(int, E) method to verify IndexOutOfBoundsException behavior.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddWithNegativeIndex();
        testAddWithIndexGreaterThanSize();
        testAddWithValidIndex();
    }

    private static void testAddWithNegativeIndex() {
        LinkedList<Object> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        boolean threw = false;
        try {
            list.add(-1, "x");
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testAddWithIndexGreaterThanSize() {
        LinkedList<Object> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        boolean threw = false;
        try {
            list.add(3, "x");
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }

    private static void testAddWithValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add(1, "x");
        if (!list.get(1).equals("x")) {
            throw new AssertionError("Element not inserted at correct position");
        }
        if (list.size() != 3) {
            throw new AssertionError("List size incorrect after insertion");
        }
    }
}
