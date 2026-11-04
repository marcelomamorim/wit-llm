/*
 * @test
 * @summary Checks public ListIterator.previous() behavior of immutable list iterators (ImmutableCollections.ListItr)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousFromEnd();
        testPreviousOnEmptyListThrowsNoSuchElement();
        testPreviousAtStartThrowsNoSuchElementAndKeepsCursor();
        testPreviousThenNextReturnsSameElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testPreviousFromEnd() {
        List<String> list = List.of("a", "b", "c");
        ListIterator<String> it = list.listIterator(list.size());
        check(it.previous().equals("c"), "first previous should return last element");
        check(it.previousIndex() == 1, "previousIndex should be 1 after one previous");
        check(it.previous().equals("b"), "second previous should return middle element");
        check(it.previous().equals("a"), "third previous should return first element");
        check(!it.hasPrevious(), "iterator should have no previous elements");
        check(it.nextIndex() == 0, "nextIndex should be 0 at start");
    }

    static void testPreviousOnEmptyListThrowsNoSuchElement() {
        ListIterator<Object> it = List.of().listIterator();
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous on empty immutable list iterator should throw NoSuchElementException");
    }

    static void testPreviousAtStartThrowsNoSuchElementAndKeepsCursor() {
        List<Integer> list = List.of(1, 2, 3);
        ListIterator<Integer> it = list.listIterator();
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "previous at index 0 should throw NoSuchElementException");
        check(it.nextIndex() == 0, "cursor should remain 0 after failed previous");
        check(it.next().equals(1), "next should still return first element");
    }

    static void testPreviousThenNextReturnsSameElement() {
        List<Integer> list = List.of(1);
        ListIterator<Integer> it = list.listIterator(1);
        check(it.previous().equals(1), "previous should return the single element");
        check(it.hasNext(), "iterator should have next after previous");
        check(it.next().equals(1), "next after previous should return the same element");
    }
}
