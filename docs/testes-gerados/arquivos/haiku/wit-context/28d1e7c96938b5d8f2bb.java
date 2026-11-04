/*
 * @test
 * @summary Observable behavior of ImmutableCollections ListItr.hasPrevious via public List iterators
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);

        ListIterator<Integer> start = list.listIterator();
        check(!start.hasPrevious(), "hasPrevious must be false at index 0");

        start.next();
        check(start.hasPrevious(), "hasPrevious must be true after next()");

        ListIterator<Integer> end = list.listIterator(list.size());
        check(end.hasPrevious(), "hasPrevious must be true at the end of a non-empty list");

        List<Integer> empty = List.of();
        check(!empty.listIterator().hasPrevious(), "hasPrevious must be false for an empty list");

        Iterator<Integer> plain = list.iterator();
        boolean thrown = false;
        try {
            ((ListIterator<Integer>) plain).hasPrevious();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "hasPrevious on a non-ListIterator view must throw UnsupportedOperationException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
