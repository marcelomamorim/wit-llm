/*
 * @test
 * @summary Test CopyOnWriteArrayList ListIterator.previous() method behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testPreviousReturnsCorrectElement();
        testPreviousThrowsWhenNoPrevious();
    }

    private static void testPreviousReturnsCorrectElement() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        ListIterator<String> it = list.listIterator(list.size()); // cursor at end

        // previous() should return last element "c"
        String prev = it.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected previous() to return 'c', got: " + prev);
        }

        // previous() again should return "b"
        prev = it.previous();
        if (!"b".equals(prev)) {
            throw new AssertionError("Expected previous() to return 'b', got: " + prev);
        }
    }

    private static void testPreviousThrowsWhenNoPrevious() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ListIterator<String> it = list.listIterator(); // empty list, cursor at 0

        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling previous() with no previous element");
        }

        // Also test when cursor is at start of non-empty list
        list = new CopyOnWriteArrayList<>(new String[] {"x"});
        it = list.listIterator(); // cursor at 0
        thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling previous() at start of list");
        }
    }
}
