/*
 * @test
 * @summary Verify LinkedList.removeFirst throws NoSuchElementException on an empty list and removes the head otherwise
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOnEmptyListThrows();
        testRemoveFirstReturnsAndRemovesHead();
        testRemoveFirstAfterDrainingThrows();
        testRemoveFirstReturnsNullElement();
        System.out.println("PASSED");
    }

    private static void testRemoveFirstOnEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException on empty list");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("List should remain empty after failed removeFirst");
        }
    }

    private static void testRemoveFirstReturnsAndRemovesHead() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c"));
        String first = list.removeFirst();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected head 'a' but was " + first);
        }
        if (list.size() != 2) {
            throw new RuntimeException("Expected size 2 but was " + list.size());
        }
        if (!"b".equals(list.getFirst())) {
            throw new RuntimeException("Expected new head 'b' but was " + list.getFirst());
        }
    }

    private static void testRemoveFirstAfterDrainingThrows() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2));
        list.removeFirst();
        list.removeFirst();
        boolean thrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException after draining list");
        }
    }

    private static void testRemoveFirstReturnsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("x");
        String first = list.removeFirst();
        if (first != null) {
            throw new RuntimeException("Expected null head but was " + first);
        }
        if (list.size() != 1 || !"x".equals(list.getFirst())) {
            throw new RuntimeException("Unexpected list state after removing null head");
        }
    }
}
