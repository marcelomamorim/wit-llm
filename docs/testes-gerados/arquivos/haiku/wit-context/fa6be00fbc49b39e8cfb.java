/*
 * @test
 * @summary Witup-derived behavioral tests for ArrayList.ListItr.previous() via public listIterator API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousReturnsElementsInReverseOrder();
        testPreviousReturnsNullElement();
        testPreviousOnEmptyListThrowsNoSuchElementException();
        testPreviousAtStartThrowsNoSuchElementException();
        testPreviousDetectsConcurrentModification();
    }

    static void testPreviousReturnsElementsInReverseOrder() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        ListIterator<String> it = list.listIterator(list.size());
        check("c".equals(it.previous()), "first previous should be c");
        check("b".equals(it.previous()), "second previous should be b");
        check("a".equals(it.previous()), "third previous should be a");
        expectNoSuchElement(it);
    }

    static void testPreviousReturnsNullElement() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("x", null));
        ListIterator<String> it = list.listIterator(list.size());
        check(it.previous() == null, "previous should return stored null");
        check("x".equals(it.previous()), "next previous should return x");
    }

    static void testPreviousOnEmptyListThrowsNoSuchElementException() {
        ArrayList<String> list = new ArrayList<>();
        ListIterator<String> it = list.listIterator(0);
        expectNoSuchElement(it);
    }

    static void testPreviousAtStartThrowsNoSuchElementException() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        ListIterator<String> it = list.listIterator(0);
        expectNoSuchElement(it);
    }

    static void testPreviousDetectsConcurrentModification() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        ListIterator<String> it = list.listIterator(list.size());
        list.add("c");
        boolean thrown = false;
        try {
            it.previous();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "previous after structural modification should throw ConcurrentModificationException");
    }

    static void expectNoSuchElement(ListIterator<?> it) {
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous should throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
