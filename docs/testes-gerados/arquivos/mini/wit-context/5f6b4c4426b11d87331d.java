/*
 * @test
 * @summary Test CopyOnWriteArrayList.COWIterator.previous() method behavior including NoSuchElementException
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousWithValidCursor();
        testPreviousThrowsNoSuchElementException();
    }

    private static void testPreviousWithValidCursor() {
        // Create a CopyOnWriteArrayList with some elements
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        // Get iterator
        CopyOnWriteArrayList<String>.COWIterator it = list.new COWIterator(3); // cursor at end

        // previous() should return elements in reverse order
        String prev = it.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected 'c' but got " + prev);
        }
        prev = it.previous();
        if (!"b".equals(prev)) {
            throw new AssertionError("Expected 'b' but got " + prev);
        }
        prev = it.previous();
        if (!"a".equals(prev)) {
            throw new AssertionError("Expected 'a' but got " + prev);
        }
    }

    private static void testPreviousThrowsNoSuchElementException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String>.COWIterator it = list.new COWIterator(0); // empty list, cursor at 0

        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling previous() with cursor <= 0");
        }

        // Also test iterator with cursor at 0 on non-empty list
        CopyOnWriteArrayList<String> list2 = new CopyOnWriteArrayList<>(new String[] {"x"});
        CopyOnWriteArrayList<String>.COWIterator it2 = list2.new COWIterator(0);
        thrown = false;
        try {
            it2.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling previous() with cursor <= 0 on non-empty list");
        }
    }
}
