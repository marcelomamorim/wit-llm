/*
 * @test
 * @summary Behavioral tests for ArrayList iterator forEachRemaining
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingConsumesOnlyRemainingElements();
        testForEachRemainingOnEmptyList();
        testNullActionThrowsNullPointerException();
        testComodificationDuringActionThrows();
        testRemoveAfterForEachRemainingRemovesLastReturned();
        System.out.println("PASS");
    }

    static void testForEachRemainingConsumesOnlyRemainingElements() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "first next() should return 1");

        List<Integer> seen = new ArrayList<>();
        Consumer<Integer> action = seen::add;
        it.forEachRemaining(action);

        check(seen.size() == 2, "expected 2 remaining elements, got " + seen.size());
        check(seen.get(0) == 2 && seen.get(1) == 3, "unexpected remaining elements: " + seen);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    static void testForEachRemainingOnEmptyList() {
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> it = list.iterator();

        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);

        check(seen.isEmpty(), "action must not be called on empty list");
        check(!it.hasNext(), "iterator over empty list should have no next");
    }

    static void testNullActionThrowsNullPointerException() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Iterator<Integer> it = list.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testComodificationDuringActionThrows() {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();

        final List<Integer> calls = new ArrayList<>();
        Consumer<Integer> action = x -> {
            calls.add(x);
            list.add(99);
        };

        boolean thrown = false;
        try {
            it.forEachRemaining(action);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during action must throw ConcurrentModificationException");
        check(calls.size() == 1 && calls.get(0) == 1,
              "action should have been called once with 1, calls=" + calls);
    }

    static void testRemoveAfterForEachRemainingRemovesLastReturned() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "first next() should return 1");

        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 3, "expected 3 elements consumed, got " + seen.size());

        it.remove();
        check(list.size() == 3, "remove() should delete last returned element, size=" + list.size());
        check(list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 3,
              "unexpected list content after remove");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
