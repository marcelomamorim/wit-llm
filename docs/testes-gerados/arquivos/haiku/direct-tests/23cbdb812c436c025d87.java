/*
 * @test
 * @summary Checks CopyOnWriteArrayList.listIterator(int) positioning, bounds checking, snapshot semantics and unsupported iterator mutations
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }

    static void testIteratorStartsAtIndex() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});

        ListIterator<String> it = list.listIterator(0);
        check(it.nextIndex() == 0, "nextIndex at 0 expected 0");
        check(it.hasNext(), "iterator at 0 should have next");
        check("a".equals(it.next()), "first element from index 0 should be a");

        it = list.listIterator(1);
        check(it.nextIndex() == 1, "nextIndex at 1 expected 1");
        check("b".equals(it.next()), "element at index 1 should be b");
    }

    static void testIteratorAtEndAllowed() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});

        ListIterator<String> it = list.listIterator(3);
        check(!it.hasNext(), "iterator at size should have no next");
        check(it.nextIndex() == 3, "nextIndex at size expected 3");
        check(it.previousIndex() == 2, "previousIndex at size expected 2");
        check("c".equals(it.previous()), "previous from size should be c");
    }

    static void testEmptyListIndexZero() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ListIterator<String> it = list.listIterator(0);
        check(!it.hasNext(), "empty list iterator should have no next");
        check(!it.hasPrevious(), "empty list iterator should have no previous");
        check(it.nextIndex() == 0, "empty list nextIndex expected 0");
    }

    static void testOutOfBoundsNegative() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a"});
        try {
            list.listIterator(-1);
            fail("listIterator(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testOutOfBoundsPastSize() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        try {
            list.listIterator(3);
            fail("listIterator(size+1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testOutOfBoundsOnEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.listIterator(1);
            fail("listIterator(1) on empty list should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testSnapshotIgnoresLaterAdds() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        ListIterator<String> it = list.listIterator(0);
        list.add("d");

        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        check(count == 3, "snapshot iterator should see 3 elements, saw " + count);
        check(list.size() == 4, "list should contain 4 elements after add");
    }

    static void testIteratorRemoveUnsupported() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        ListIterator<String> it = list.listIterator(0);
        it.next();
        try {
            it.remove();
            fail("ListIterator.remove should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        check(list.size() == 2, "list must be unchanged after failed remove");
    }

    public static void main(String[] args) {
        testIteratorStartsAtIndex();
        testIteratorAtEndAllowed();
        testEmptyListIndexZero();
        testOutOfBoundsNegative();
        testOutOfBoundsPastSize();
        testOutOfBoundsOnEmptyList();
        testSnapshotIgnoresLaterAdds();
        testIteratorRemoveUnsupported();
    }
}
