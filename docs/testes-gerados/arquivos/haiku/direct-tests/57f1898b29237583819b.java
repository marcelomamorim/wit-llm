/*
 * @test
 * @summary White-box-free behavioral tests for LinkedList.getFirst()
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testEmptyListThrowsNoSuchElement();
        testReturnsFirstElement();
        testReturnsNullWhenFirstElementIsNull();
        testReflectsAddFirst();
        testThrowsAfterLastElementRemoved();
        testDoesNotModifyList();
    }

    static void testEmptyListThrowsNoSuchElement() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testReturnsFirstElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String first = list.getFirst();
        if (!"a".equals(first)) {
            throw new RuntimeException("expected a but got " + first);
        }
    }

    static void testReturnsNullWhenFirstElementIsNull() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("x");
        if (list.getFirst() != null) {
            throw new RuntimeException("expected null first element");
        }
    }

    static void testReflectsAddFirst() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.addFirst(0);
        Integer first = list.getFirst();
        if (first.intValue() != 0) {
            throw new RuntimeException("expected 0 but got " + first);
        }
    }

    static void testThrowsAfterLastElementRemoved() {
        LinkedList<String> list = new LinkedList<>();
        list.add("only");
        list.removeFirst();
        try {
            list.getFirst();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testDoesNotModifyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.getFirst();
        if (list.size() != 2) {
            throw new RuntimeException("size changed to " + list.size());
        }
        if (!"b".equals(list.get(1))) {
            throw new RuntimeException("second element changed");
        }
    }
}
