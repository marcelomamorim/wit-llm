/*
 * @test
 * @summary Verifies ListIterator.nextIndex() behavior on iterators of immutable collections, including the non-ListIterator path that throws UnsupportedOperationException.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testIteratorNextIndexThrowsUnsupported();
        testListIteratorNextIndexStartsAtZero();
        testListIteratorNextIndexAdvancesAfterNext();
        testListIteratorNextIndexHonorsStartIndex();
    }

    static void testIteratorNextIndexThrowsUnsupported() {
        List<Integer> list = List.of(1, 2, 3);
        Iterator<Integer> it = list.iterator();
        ListIterator<Integer> li = (ListIterator<Integer>) it;
        boolean thrown = false;
        try {
            li.nextIndex();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("nextIndex() on non-list iterator did not throw UnsupportedOperationException");
        }
    }

    static void testListIteratorNextIndexStartsAtZero() {
        List<Integer> list = List.of(1, 2, 3);
        ListIterator<Integer> li = list.listIterator();
        check(li.nextIndex() == 0, "expected nextIndex() == 0 at start, got " + li.nextIndex());
    }

    static void testListIteratorNextIndexAdvancesAfterNext() {
        List<Integer> list = List.of(10, 20, 30);
        ListIterator<Integer> li = list.listIterator();
        li.next();
        check(li.nextIndex() == 1, "expected nextIndex() == 1 after one next()");
        li.next();
        check(li.nextIndex() == 2, "expected nextIndex() == 2 after two next()");
    }

    static void testListIteratorNextIndexHonorsStartIndex() {
        List<Integer> list = List.of(1, 2, 3, 4);
        ListIterator<Integer> li = list.listIterator(2);
        check(li.nextIndex() == 2, "expected nextIndex() == 2 for listIterator(2)");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
