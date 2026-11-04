/*
 * @test
 * @summary WITUP-derived tests for COWSubListIterator.forEachRemaining via public subList().listIterator()
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionThrowsNpeOnEmptySubList();
        testForEachRemainingVisitsRemainingElements();
        testForEachRemainingOnExhaustedIteratorVisitsNothing();
    }

    static CopyOnWriteArrayList<Integer> newList() {
        return new CopyOnWriteArrayList<>(new Integer[]{0, 1, 2, 3, 4});
    }

    static void testNullActionThrowsNpe() {
        ListIterator<Integer> it = newList().subList(1, 4).listIterator();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullActionThrowsNpeOnEmptySubList() {
        ListIterator<Integer> it = newList().subList(0, 0).listIterator();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachRemainingVisitsRemainingElements() {
        ListIterator<Integer> it = newList().subList(1, 4).listIterator(1);
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        if (!seen.equals(Arrays.asList(2, 3))) {
            throw new RuntimeException("unexpected elements: " + seen);
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
    }

    static void testForEachRemainingOnExhaustedIteratorVisitsNothing() {
        ListIterator<Integer> it = newList().subList(1, 4).listIterator(3);
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        if (!seen.isEmpty()) {
            throw new RuntimeException("expected no elements: " + seen);
        }
    }
}
