/*
 * @test
 * @summary Behavioral checks for LinkedList spliterator tryAdvance (null action, empty list, element order, fail-fast CME)
 * @run main LinkedListWitupTest
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ConcurrentModificationException;
import java.util.Spliterator;

public class LinkedListWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        Spliterator<Integer> spl = list.spliterator();
        boolean npe = false;
        try {
            spl.tryAdvance(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyListReturnsFalse() {
        LinkedList<Integer> list = new LinkedList<>();
        Spliterator<Integer> spl = list.spliterator();
        ArrayList<Integer> seen = new ArrayList<>();
        boolean r = spl.tryAdvance(x -> seen.add(x));
        check(!r, "tryAdvance on empty list must return false");
        check(seen.isEmpty(), "action must not be invoked on empty list");
    }

    static void testElementsInOrderOneAtATime() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        Spliterator<Integer> spl = list.spliterator();
        ArrayList<Integer> seen = new ArrayList<>();
        check(spl.tryAdvance(x -> seen.add(x)), "first tryAdvance must return true");
        check(spl.tryAdvance(x -> seen.add(x)), "second tryAdvance must return true");
        check(spl.tryAdvance(x -> seen.add(x)), "third tryAdvance must return true");
        check(!spl.tryAdvance(x -> seen.add(x)), "fourth tryAdvance must return false");
        check(seen.size() == 3, "expected 3 elements, got " + seen.size());
        check(seen.get(0) == 10 && seen.get(1) == 20 && seen.get(2) == 30,
              "elements must be visited in list order: " + seen);
    }

    static void testNullElementVisited() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        Spliterator<String> spl = list.spliterator();
        ArrayList<String> seen = new ArrayList<>();
        check(spl.tryAdvance(x -> seen.add(x)), "tryAdvance must return true for null element");
        check(seen.size() == 1 && seen.get(0) == null, "null element must be passed to action");
    }

    static void testStructuralModificationInActionThrowsCME() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> spl = list.spliterator();
        boolean cme = false;
        try {
            spl.tryAdvance(x -> list.add(99));
        } catch (ConcurrentModificationException e) {
            cme = true;
        }
        check(cme, "structural modification during action must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyListReturnsFalse();
        testElementsInOrderOneAtATime();
        testNullElementVisited();
        testStructuralModificationInActionThrowsCME();
    }
}
