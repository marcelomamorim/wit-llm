/*
 * @test
 * @summary Behavioral tests for ArrayDeque iterator forEachRemaining via the public Iterator interface
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOrderedTraversal();
        testWrapAround();
        testEmptyDeque();
        testNullActionThrowsNPE();
        testConcurrentModificationDetected();
        testIteratorExhaustedAfterForEach();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testOrderedTraversal() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.add(1);
        d.add(2);
        d.add(3);
        Iterator<Integer> it = d.iterator();
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(out::add);
        check(out.equals(List.of(1, 2, 3)), "expected [1, 2, 3] but got " + out);
    }

    static void testWrapAround() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        Iterator<Integer> it = d.iterator();
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(out::add);
        check(out.equals(List.of(3, 2, 1)), "expected [3, 2, 1] but got " + out);
    }

    static void testEmptyDeque() {
        ArrayDeque<String> d = new ArrayDeque<>();
        Iterator<String> it = d.iterator();
        int[] count = new int[1];
        it.forEachRemaining(s -> count[0]++);
        check(count[0] == 0, "action should not be called for empty deque");
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("a");
        Iterator<String> it = d.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testConcurrentModificationDetected() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.add(1);
        d.add(2);
        d.add(3);
        Iterator<Integer> it = d.iterator();
        d.addLast(4);
        boolean thrown = false;
        try {
            it.forEachRemaining(x -> { });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after modification");
    }

    static void testIteratorExhaustedAfterForEach() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.add(10);
        d.add(20);
        Iterator<Integer> it = d.iterator();
        it.forEachRemaining(x -> { });
        check(!it.hasNext(), "iterator should have no remaining elements");
    }
}
