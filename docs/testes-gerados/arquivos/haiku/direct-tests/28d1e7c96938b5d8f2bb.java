/*
 * @test
 * @summary Verifies ListIterator.hasPrevious() on immutable list iterators from List.of
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyListIterator() {
        ListIterator<Integer> it = List.<Integer>of().listIterator();
        check(!it.hasPrevious(), "empty list: hasPrevious must be false at start");
    }

    static void testStartOfListIterator() {
        ListIterator<Integer> it = List.of(1, 2, 3).listIterator();
        check(!it.hasPrevious(), "cursor 0: hasPrevious must be false");
        it.next();
        check(it.hasPrevious(), "cursor 1: hasPrevious must be true");
        it.previous();
        check(!it.hasPrevious(), "back at cursor 0: hasPrevious must be false");
    }

    static void testMiddleAndEndOfListIterator() {
        ListIterator<Integer> it = List.of(1, 2, 3).listIterator();
        it.next();
        it.next();
        check(it.hasPrevious(), "cursor 2: hasPrevious must be true");
        it.next();
        check(it.hasPrevious(), "cursor 3 (end): hasPrevious must be true");
    }

    static void testListIteratorAtEndIndex() {
        List<Integer> list = List.of(10, 20);
        ListIterator<Integer> it = list.listIterator(list.size());
        check(it.hasPrevious(), "index size(): hasPrevious must be true");
        check(it.previous() == 20, "previous() must return last element");
        check(it.hasPrevious(), "after one previous(): hasPrevious must still be true");
        check(it.previous() == 10, "previous() must return first element");
        check(!it.hasPrevious(), "after consuming all: hasPrevious must be false");
    }

    static void testSingleElementList() {
        ListIterator<String> it = List.of("a").listIterator();
        check(!it.hasPrevious(), "single element at start: hasPrevious must be false");
        it.next();
        check(it.hasPrevious(), "single element after next: hasPrevious must be true");
    }

    public static void main(String[] args) {
        testEmptyListIterator();
        testStartOfListIterator();
        testMiddleAndEndOfListIterator();
        testListIteratorAtEndIndex();
        testSingleElementList();
    }
}
