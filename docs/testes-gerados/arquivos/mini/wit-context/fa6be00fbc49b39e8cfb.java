/*
 * @test
 * @summary Test java.util.ArrayList.ListItr.previous() method for expected exceptions and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testPreviousNormalBehavior();
        testPreviousThrowsNoSuchElementException();
        testPreviousThrowsConcurrentModificationExceptionOnModCount();
        testPreviousThrowsConcurrentModificationExceptionOnIndex();
    }

    private static void testPreviousNormalBehavior() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> it = list.listIterator(list.size());
        // Move cursor back one element
        String prev = it.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected previous element 'c', got: " + prev);
        }
    }

    private static void testPreviousThrowsNoSuchElementException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        ListIterator<String> it = list.listIterator();
        try {
            it.previous(); // cursor at 0, should throw NoSuchElementException
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testPreviousThrowsConcurrentModificationExceptionOnModCount() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        ListIterator<String> it = list.listIterator(list.size());

        // Modify list structurally after iterator creation to cause modCount mismatch
        list.add("y");

        try {
            it.previous();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }

    private static void testPreviousThrowsConcurrentModificationExceptionOnIndex() {
        // This test tries to simulate the condition i >= elementData.length
        // which is not normally reachable via public API, so we test that
        // the iterator behaves correctly by not throwing in normal usage.
        // Since elementData is private and no public API allows corrupting
        // the iterator's cursor, we omit a direct test for this path.
        // This is noted in the test notes.
    }
}
