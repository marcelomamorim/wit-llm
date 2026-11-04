/*
 * @test
 * @summary Verifies ListIterator.previous() on immutable lists returns elements in reverse order and throws NoSuchElementException when no previous element exists
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testMultiElementReverseTraversal();
        testNoSuchElementAtBeginning();
        testSingleElement();
        testEmptyList();
        testListNPath();
    }

    static void testMultiElementReverseTraversal() {
        List<Integer> list = List.of(1, 2, 3);
        ListIterator<Integer> it = list.listIterator();
        check(it.previous() == 3, "first previous should be 3");
        check(it.previous() == 2, "second previous should be 2");
        check(it.previous() == 1, "third previous should be 1");
        check(!it.hasPrevious(), "no previous elements should remain");
    }

    static void testNoSuchElementAtBeginning() {
        List<Integer> list = List.of(1, 2, 3);
        ListIterator<Integer> it = list.listIterator();
        it.previous();
        it.previous();
        it.previous();
        boolean threw = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check(threw, "previous() past beginning should throw NoSuchElementException");
        check(!it.hasPrevious(), "cursor should remain at beginning after failed previous()");
        check(it.next() == 1, "next() after failed previous() should return first element");
    }

    static void testSingleElement() {
        List<String> list = List.of("a");
        ListIterator<String> it = list.listIterator();
        check("a".equals(it.previous()), "previous should return the single element");
        boolean threw = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check(threw, "second previous() on single-element list should throw NoSuchElementException");
    }

    static void testEmptyList() {
        List<String> list = List.of();
        ListIterator<String> it = list.listIterator();
        boolean threw = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check(threw, "previous() on empty immutable list should throw NoSuchElementException");
    }

    static void testListNPath() {
        List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ListIterator<Integer> it = list.listIterator();
        check(it.previous() == 10, "last element should be returned first");
        check(it.previous() == 9, "previous should continue in reverse order");
        for (int i = 8; i >= 0; i--) {
            check(it.previous() == i, "reverse traversal mismatch at " + i);
        }
        boolean threw = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check(threw, "previous() past beginning of ListN should throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
