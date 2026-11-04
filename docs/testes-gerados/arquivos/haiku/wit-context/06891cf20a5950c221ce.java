/*
 * @test
 * @summary Checks LinkedList spliterator tryAdvance: null action NPE, element traversal, empty list, and fail-fast CME
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkedListWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        Spliterator<String> s = list.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testTraversesElementsInOrder() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        Spliterator<String> s = list.spliterator();
        StringBuilder sb = new StringBuilder();
        Consumer<String> c = sb::append;
        check(s.tryAdvance(c), "first tryAdvance should return true");
        check(s.tryAdvance(c), "second tryAdvance should return true");
        check(s.tryAdvance(c), "third tryAdvance should return true");
        check(!s.tryAdvance(c), "tryAdvance past end should return false");
        check(sb.toString().equals("xyz"), "unexpected order: " + sb);
    }

    static void testEmptyListReturnsFalseWithoutAccept() {
        LinkedList<String> list = new LinkedList<>();
        Spliterator<String> s = list.spliterator();
        int[] calls = new int[1];
        boolean r = s.tryAdvance(e -> calls[0]++);
        check(!r, "empty list tryAdvance should return false");
        check(calls[0] == 0, "action must not be called for empty list");
    }

    static void testNullElementIsDelivered() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        Spliterator<String> s = list.spliterator();
        String[] holder = new String[1];
        boolean[] called = new boolean[1];
        boolean r = s.tryAdvance(e -> {
            holder[0] = e;
            called[0] = true;
        });
        check(r, "tryAdvance should return true for null element");
        check(called[0], "action should be called for null element");
        check(holder[0] == null, "null element should be delivered as null");
    }

    static void testStructuralModificationThrowsCME() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        Spliterator<String> s = list.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(e -> list.add("c"));
        } catch (ConcurrentModificationException ex) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTraversesElementsInOrder();
        testEmptyListReturnsFalseWithoutAccept();
        testNullElementIsDelivered();
        testStructuralModificationThrowsCME();
    }
}
