/*
 * @test
 * @summary Verifies ArrayDeque iterator forEachRemaining behavior: remaining elements, exhausted iterator, empty deque, and null action
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemainingElementsAfterFirstNext();
        testExhaustedIteratorDoesNothing();
        testEmptyDequeIterator();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testRemainingElementsAfterFirstNext() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        d.addLast(4);
        Iterator<Integer> it = d.iterator();
        check(it.next() == 1, "first element should be 1");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(2, 3, 4)), "remaining elements should be 2,3,4 but was " + seen);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    static void testExhaustedIteratorDoesNothing() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(10);
        d.addLast(20);
        Iterator<Integer> it = d.iterator();
        List<Integer> first = new ArrayList<>();
        it.forEachRemaining(first::add);
        check(first.equals(Arrays.asList(10, 20)), "first pass should visit 10,20 but was " + first);
        List<Integer> second = new ArrayList<>();
        it.forEachRemaining(second::add);
        check(second.isEmpty(), "second forEachRemaining on exhausted iterator should visit nothing");
    }

    static void testEmptyDequeIterator() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        Iterator<Integer> it = d.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty deque iterator should visit nothing");
        check(!it.hasNext(), "empty deque iterator should have no next");
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(1);
        Iterator<Integer> it = d.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
    }
}
