/*
 * @test
 * @summary Checks ListIterator.previousIndex() on immutable list iterators and the UnsupportedOperationException path of non-ListIterator iterators
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testListIteratorPreviousIndex() {
        List<String> list = List.of("a", "b", "c");
        ListIterator<String> it = list.listIterator();
        check(it.previousIndex() == -1, "previousIndex at start should be -1");
        it.next();
        check(it.previousIndex() == 0, "previousIndex after one next should be 0");
        it.next();
        check(it.previousIndex() == 1, "previousIndex after two next should be 1");
        it.next();
        check(it.previousIndex() == 2, "previousIndex after three next should be 2");
    }

    static void testIteratorPreviousIndexThrowsIfListIterator() {
        Iterator<String> it = List.of("x", "y").iterator();
        Object o = it;
        if (o instanceof ListIterator) {
            ListIterator<?> li = (ListIterator<?>) o;
            boolean thrown = false;
            try {
                li.previousIndex();
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
            check(thrown, "previousIndex on non-ListIterator view should throw UnsupportedOperationException");
        }
    }

    public static void main(String[] args) {
        testListIteratorPreviousIndex();
        testIteratorPreviousIndexThrowsIfListIterator();
    }
}
