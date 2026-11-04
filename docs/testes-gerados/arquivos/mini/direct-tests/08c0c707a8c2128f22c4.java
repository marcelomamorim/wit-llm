/*
 * @test
 * @summary Test LinkedList.add(int, E) method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddAtBeginning();
        testAddAtMiddle();
        testAddAtEnd();
        testAddNullElement();
        testAddIndexOutOfBounds();
    }

    private static void testAddAtBeginning() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "first");
        if (!"first".equals(list.get(0))) {
            throw new AssertionError("Element at index 0 should be 'first'");
        }
        if (list.size() != 1) {
            throw new AssertionError("Size should be 1 after adding at beginning");
        }
    }

    private static void testAddAtMiddle() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "a");
        list.add(1, "c");
        list.add(1, "b");
        if (!"b".equals(list.get(1))) {
            throw new AssertionError("Element at index 1 should be 'b'");
        }
        if (list.size() != 3) {
            throw new AssertionError("Size should be 3 after adding in middle");
        }
    }

    private static void testAddAtEnd() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        if (!"c".equals(list.get(2))) {
            throw new AssertionError("Element at index 2 should be 'c'");
        }
        if (list.size() != 3) {
            throw new AssertionError("Size should be 3 after adding at end");
        }
    }

    private static void testAddNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, null);
        if (list.size() != 1) {
            throw new AssertionError("Size should be 1 after adding null element");
        }
        if (list.get(0) != null) {
            throw new AssertionError("Element at index 0 should be null");
        }
    }

    private static void testAddIndexOutOfBounds() {
        LinkedList<String> list = new LinkedList<>();
        boolean caught = false;
        try {
            list.add(1, "x"); // index > size (0)
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        }

        caught = false;
        try {
            list.add(-1, "x"); // negative index
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }
}
