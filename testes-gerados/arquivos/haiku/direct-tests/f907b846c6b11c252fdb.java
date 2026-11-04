/*
 * @test
 * @summary LinkedList spliterator forEachRemaining: traversal, null action, empty list, partial traversal and fail-fast behavior
 * @run main LinkedListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;

public class LinkedListWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testCollectsAllInOrder() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> out = new ArrayList<>();
        list.spliterator().forEachRemaining(out::add);
        check(out.equals(List.of(1, 2, 3)) || out.equals(java.util.Arrays.asList(1, 2, 3)),
              "expected [1, 2, 3] but got " + out);
    }

    static void testEmptyListNoAction() {
        LinkedList<String> list = new LinkedList<>();
        List<String> out = new ArrayList<>();
        list.spliterator().forEachRemaining(out::add);
        check(out.isEmpty(), "empty list must not invoke action");
    }

    static void testNullActionThrowsNPE() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        boolean thrown = false;
        try {
            list.spliterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testPartialTraversalAfterTryAdvance() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        Spliterator<Integer> sp = list.spliterator();
        List<Integer> out = new ArrayList<>();
        sp.tryAdvance(out::add);
        sp.forEachRemaining(out::add);
        check(out.size() == 3 && out.get(0) == 10 && out.get(1) == 20 && out.get(2) == 30,
              "expected [10, 20, 30] but got " + out);
    }

    static void testSecondCallIsNoop() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(5);
        Spliterator<Integer> sp = list.spliterator();
        List<Integer> out = new ArrayList<>();
        sp.forEachRemaining(out::add);
        sp.forEachRemaining(out::add);
        check(out.size() == 1 && out.get(0) == 5,
              "second forEachRemaining must not re-visit elements, got " + out);
    }

    static void testConcurrentModificationDetected() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> sp = list.spliterator();
        sp.estimateSize();
        list.add(4);
        List<Integer> out = new ArrayList<>();
        boolean cme = false;
        try {
            sp.forEachRemaining(out::add);
        } catch (ConcurrentModificationException e) {
            cme = true;
        }
        check(cme, "structural modification must cause ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testCollectsAllInOrder();
        testEmptyListNoAction();
        testNullActionThrowsNPE();
        testPartialTraversalAfterTryAdvance();
        testSecondCallIsNoop();
        testConcurrentModificationDetected();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
