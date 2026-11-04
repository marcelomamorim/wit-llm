/*
 * @test
 * @summary Behavioral tests for ArrayList.ListItr.previous() via the public ListIterator API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPreviousReturnsElementsInReverseOrder() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(3);
        check(it.hasPrevious(), "hasPrevious should be true at end");
        check("c".equals(it.previous()), "first previous() should return c");
        check("b".equals(it.previous()), "second previous() should return b");
        check(it.hasPrevious(), "hasPrevious should be true before first element");
        check("a".equals(it.previous()), "third previous() should return a");
        check(!it.hasPrevious(), "hasPrevious should be false at start");
        check(it.nextIndex() == 0, "nextIndex should be 0 after exhausting previous");
    }

    static void testPreviousOnEmptyListThrowsNoSuchElement() {
        ArrayList<String> list = new ArrayList<>();
        ListIterator<String> it = list.listIterator();
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous() on empty list should throw NoSuchElementException");
    }

    static void testPreviousAtStartThrowsNoSuchElement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ListIterator<Integer> it = list.listIterator();
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous() at index 0 should throw NoSuchElementException");
    }

    static void testPreviousAfterStructuralModificationThrowsCME() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        ListIterator<String> it = list.listIterator(2);
        list.add("z");
        boolean thrown = false;
        try {
            it.previous();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "previous() after external add should throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testPreviousReturnsElementsInReverseOrder();
        testPreviousOnEmptyListThrowsNoSuchElement();
        testPreviousAtStartThrowsNoSuchElement();
        testPreviousAfterStructuralModificationThrowsCME();
    }
}
