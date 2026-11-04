/*
 * @test
 * @summary Verifies LinkedList.removeLast() returns the last element and throws NoSuchElementException when the list is empty
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveLastOnEmptyListThrows();
        testRemoveLastReturnsAndRemovesLastElement();
        testRemoveLastOnSingleElementList();
        testRemoveLastRepeatedUntilEmpty();
        testRemoveLastAllowsNullElement();
    }

    static void testRemoveLastOnEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException on empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List must remain empty after failed removeLast");
        }
    }

    static void testRemoveLastReturnsAndRemovesLastElement() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c"));
        String removed = list.removeLast();
        if (!"c".equals(removed)) {
            throw new AssertionError("Expected 'c' but was " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2 but was " + list.size());
        }
        if (!"b".equals(list.getLast())) {
            throw new AssertionError("Expected new last element 'b'");
        }
    }

    static void testRemoveLastOnSingleElementList() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(42));
        Integer removed = list.removeLast();
        if (removed.intValue() != 42) {
            throw new AssertionError("Expected 42 but was " + removed);
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List must be empty after removing its only element");
        }
    }

    static void testRemoveLastRepeatedUntilEmpty() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));
        if (list.removeLast() != 3 || list.removeLast() != 2 || list.removeLast() != 1) {
            throw new AssertionError("removeLast must return elements in reverse order");
        }
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException after draining list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testRemoveLastAllowsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add(null);
        String removed = list.removeLast();
        if (removed != null) {
            throw new AssertionError("Expected null element to be returned");
        }
        if (list.size() != 1 || !"x".equals(list.getFirst())) {
            throw new AssertionError("Remaining list state incorrect after removing null");
        }
    }
}
