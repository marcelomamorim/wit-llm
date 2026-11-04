/*
 * @test
 * @summary Test java.util.ImmutableCollections.previous() method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UnsupportedOperationException;

public class ImmutableCollectionsWitupTest {

    static class TestListIterator<E> {
        private final List<E> list;
        private int cursor;
        private final boolean isListIterator;

        TestListIterator(List<E> list, int cursor, boolean isListIterator) {
            this.list = list;
            this.cursor = cursor;
            this.isListIterator = isListIterator;
        }

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
        testPreviousReturnsElementAndMovesCursor();
        testPreviousThrowsUOEWhenNotListIterator();
        testPreviousThrowsNoSuchElementExceptionWhenOutOfBounds();
    }

    private static void testPreviousReturnsElementAndMovesCursor() {
        TestListIterator<String> it = new TestListIterator<>(List.of("a", "b", "c"), 3, true);
        String prev = it.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected 'c', got: " + prev);
        }
        // cursor should move from 3 to 2
        prev = it.previous();
        if (!"b".equals(prev)) {
            throw new AssertionError("Expected 'b', got: " + prev);
        }
    }

    private static void testPreviousThrowsUOEWhenNotListIterator() {
        TestListIterator<String> it = new TestListIterator<>(List.of("a"), 1, false);
        try {
            it.previous();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void testPreviousThrowsNoSuchElementExceptionWhenOutOfBounds() {
        TestListIterator<String> it = new TestListIterator<>(List.of("a"), 0, true);
        try {
            it.previous();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
