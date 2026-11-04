/*
 * @test
 * @summary Test LinkedList.remove(int) method for valid and boundary indices
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveFirstElement();
        testRemoveLastElement();
        testRemoveFromEmptyListThrows();
        testRemoveNegativeIndexThrows();
        testRemoveIndexOutOfBoundsThrows();
    }

    private static void testRemoveValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected to remove 'b' but removed: " + removed);
        }
        if (list.size() != 2 || !"a".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new AssertionError("List content incorrect after removal");
        }
    }

    private static void testRemoveFirstElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        int removed = list.remove(0);
        if (removed != 10) {
            throw new AssertionError("Expected to remove 10 but removed: " + removed);
        }
        if (list.size() != 1 || list.get(0) != 20) {
            throw new AssertionError("List content incorrect after removing first element");
        }
    }

    private static void testRemoveLastElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(100);
        list.add(200);
        int removed = list.remove(1);
        if (removed != 200) {
            throw new AssertionError("Expected to remove 200 but removed: " + removed);
        }
        if (list.size() != 1 || list.get(0) != 100) {
            throw new AssertionError("List content incorrect after removing last element");
        }
    }

    private static void testRemoveFromEmptyListThrows() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.remove(0);
            throw new AssertionError("Expected IndexOutOfBoundsException for remove on empty list");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testRemoveNegativeIndexThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        try {
            list.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testRemoveIndexOutOfBoundsThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        try {
            list.remove(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
