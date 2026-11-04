/*
 * @test
 * @summary Test LinkedList.add(E) method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddToEmptyList();
        testAddToNonEmptyList();
        testAddNullElement();
    }

    private static void testAddToEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        if (list.size() != 1) {
            throw new AssertionError("Size should be 1 after adding one element");
        }
        if (!"first".equals(list.get(0))) {
            throw new AssertionError("Element added should be retrievable at index 0");
        }
    }

    private static void testAddToNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        if (list.size() != 2) {
            throw new AssertionError("Size should be 2 after adding two elements");
        }
        if (!"first".equals(list.get(0))) {
            throw new AssertionError("First element should remain at index 0");
        }
        if (!"second".equals(list.get(1))) {
            throw new AssertionError("Second element should be at index 1");
        }
    }

    private static void testAddNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        if (list.size() != 1) {
            throw new AssertionError("Size should be 1 after adding null element");
        }
        if (list.get(0) != null) {
            throw new AssertionError("Element at index 0 should be null");
        }
    }
}
