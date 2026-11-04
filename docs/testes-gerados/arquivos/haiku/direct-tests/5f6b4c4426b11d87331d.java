/*
 * @test
 * @summary Verifies ListIterator.previous() behavior of CopyOnWriteArrayList iterators
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousReturnsElementsInReverseOrder();
        testPreviousFromMiddleIndex();
        testPreviousOnEmptyListThrows();
        testPreviousAtStartThrows();
        testPreviousReturnsNullElement();
        testPreviousIgnoresListMutationAfterCreation();
    }

    static void testPreviousReturnsElementsInReverseOrder() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        ListIterator<Integer> it = list.listIterator(list.size());
        check(it.previous() == 3, "first previous should be 3");
        check(it.previous() == 2, "second previous should be 2");
        check(it.previous() == 1, "third previous should be 1");
        check(!it.hasPrevious(), "no elements should remain before cursor");
    }

    static void testPreviousFromMiddleIndex() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{10, 20, 30});
        ListIterator<Integer> it = list.listIterator(1);
        check(it.previous() == 10, "previous from index 1 should be 10");
        check(it.nextIndex() == 0, "cursor should move back to 0");
    }

    static void testPreviousOnEmptyListThrows() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        ListIterator<Integer> it = list.listIterator(0);
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous on empty list iterator must throw NoSuchElementException");
    }

    static void testPreviousAtStartThrows() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        ListIterator<String> it = list.listIterator(0);
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous at index 0 must throw NoSuchElementException");
    }

    static void testPreviousReturnsNullElement() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{null, "x"});
        ListIterator<String> it = list.listIterator(1);
        check(it.previous() == null, "null element should be returned by previous");
    }

    static void testPreviousIgnoresListMutationAfterCreation() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        ListIterator<Integer> it = list.listIterator(3);
        list.add(4);
        list.set(2, 99);
        check(it.previous() == 3, "snapshot iterator should return original element 3");
        check(it.previous() == 2, "snapshot iterator should return original element 2");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
