/*
 * @test
 * @summary Checks ArrayList iterator forEachRemaining: null action rejection, remaining element traversal and fail-fast on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testRemainingElementsInOrder();
        testNoRemainingElements();
        testEmptyListIterator();
        testConcurrentModificationDuringTraversal();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Iterator<Integer> it = list.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testRemainingElementsInOrder() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "first next() must return 1");
        final List<Integer> seen = new ArrayList<>();
        Consumer<Integer> collector = e -> seen.add(e);
        it.forEachRemaining(collector);
        check(seen.size() == 2, "expected 2 remaining elements, got " + seen.size());
        check(seen.get(0) == 2 && seen.get(1) == 3, "remaining elements must be [2, 3] in order");
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");
    }

    static void testNoRemainingElements() {
        List<Integer> list = new ArrayList<>();
        list.add(7);
        Iterator<Integer> it = list.iterator();
        it.next();
        final int[] calls = new int[1];
        it.forEachRemaining(e -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked when no elements remain");
    }

    static void testEmptyListIterator() {
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> it = list.iterator();
        final int[] calls = new int[1];
        it.forEachRemaining(e -> calls[0]++);
        check(calls[0] == 0, "empty list iterator must not invoke action");
        check(!it.hasNext(), "empty list iterator must have no next element");
    }

    static void testConcurrentModificationDuringTraversal() {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(e -> {
                if (e == 1) {
                    list.add(4);
                }
            });
        } catch (ConcurrentModificationException ex) {
            thrown = true;
        }
        check(thrown, "structural modification during forEachRemaining must throw ConcurrentModificationException");
    }
}
