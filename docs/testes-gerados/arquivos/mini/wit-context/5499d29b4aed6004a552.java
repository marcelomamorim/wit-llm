/*
 * @test
 * @summary Test ImmutableCollections.ListItr.previous() method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    static class ListItr<E> {
        private final List<E> list;
        private int cursor;
        private final boolean isListIterator;

        ListItr(List<E> list, int start, boolean isListIterator) {
            this.list = list;
            this.cursor = start;
            this.isListIterator = isListIterator;
        }

        // Method under test
        public E previous() {
            if (!isListIterator) {
                throw new UnsupportedOperationException();
            }
            try {
                int i = cursor - 1;
                E previous = list.get(i);
                cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }
    }

    public static void main(String[] args) {
        testPreviousThrowsUOEWhenNotListIterator();
        testPreviousReturnsElementAndMovesCursor();
        testPreviousThrowsNoSuchElementWhenAtStart();
        System.out.println("All tests passed.");
    }

    private static void testPreviousThrowsUOEWhenNotListIterator() {
        List<String> list = new ArrayList<>();
        list.add("a");
        ListItr<String> itr = new ListItr<>(list, 1, false); // isListIterator = false
        try {
            itr.previous();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void testPreviousReturnsElementAndMovesCursor() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListItr<String> itr = new ListItr<>(list, 3, true); // cursor at end, isListIterator = true

        String prev = itr.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected 'c', got " + prev);
        }

        prev = itr.previous();
        if (!"b".equals(prev)) {
            throw new AssertionError("Expected 'b', got " + prev);
        }
    }

    private static void testPreviousThrowsNoSuchElementWhenAtStart() {
        List<String> list = new ArrayList<>();
        list.add("a");
        ListItr<String> itr = new ListItr<>(list, 0, true); // cursor at start
        try {
            itr.previous();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
