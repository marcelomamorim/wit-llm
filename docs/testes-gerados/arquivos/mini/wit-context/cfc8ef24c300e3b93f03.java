/*
 * @test
 * @summary Test CopyOnWriteArrayList.COWSubListIterator.next() for NoSuchElementException and normal behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testNextReturnsElements();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsElements() {
        Integer[] data = {1, 2, 3};
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(data);
        ListIterator<Integer> it = list.listIterator();

        int count = 0;
        while (it.hasNext()) {
            Integer val = it.next();
            if (val == null || val != data[count]) {
                throw new AssertionError("Iterator.next() returned unexpected value");
            }
            count++;
        }
        if (count != data.length) {
            throw new AssertionError("Iterator.next() did not iterate all elements");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ListIterator<String> it = list.listIterator();

        if (it.hasNext()) {
            throw new AssertionError("Empty list iterator should not have next");
        }

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() with no next element");
        }

        // Also test iterator at end of non-empty list
        CopyOnWriteArrayList<String> list2 = new CopyOnWriteArrayList<>();
        list2.add("a");
        ListIterator<String> it2 = list2.listIterator(1); // position at end
        if (it2.hasNext()) {
            throw new AssertionError("Iterator at end should not have next");
        }
        thrown = false;
        try {
            it2.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() at end of list");
        }
    }
}
