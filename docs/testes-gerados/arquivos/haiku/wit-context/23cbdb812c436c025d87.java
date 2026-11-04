/*
 * @test
 * @summary Verify bounds checking of CopyOnWriteArrayList.listIterator(int)
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNegativeIndexThrows();
        testIndexAboveSizeThrows();
        testBoundaryIndicesAccepted();
        testEmptyListBoundaries();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIndexOutOfBounds(CopyOnWriteArrayList<String> list, int index) {
        try {
            list.listIterator(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }

    static void testNegativeIndexThrows() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<String>(new String[] {"a", "b", "c"});
        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, Integer.MIN_VALUE);
    }

    static void testIndexAboveSizeThrows() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<String>(new String[] {"a", "b", "c"});
        expectIndexOutOfBounds(list, 4);
        expectIndexOutOfBounds(list, Integer.MAX_VALUE);
    }

    static void testBoundaryIndicesAccepted() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<String>(new String[] {"a", "b", "c"});

        ListIterator<String> atStart = list.listIterator(0);
        check(atStart.nextIndex() == 0, "nextIndex at 0");
        check(!atStart.hasPrevious(), "hasPrevious at 0");
        check(atStart.next().equals("a"), "next at 0 should be a");

        ListIterator<String> atEnd = list.listIterator(3);
        check(atEnd.nextIndex() == 3, "nextIndex at size");
        check(!atEnd.hasNext(), "hasNext at size should be false");
        check(atEnd.hasPrevious(), "hasPrevious at size should be true");
        check(atEnd.previous().equals("c"), "previous at size should be c");
    }

    static void testEmptyListBoundaries() {
        CopyOnWriteArrayList<String> empty = new CopyOnWriteArrayList<String>();

        ListIterator<String> it = empty.listIterator(0);
        check(!it.hasNext(), "empty list hasNext");
        check(!it.hasPrevious(), "empty list hasPrevious");
        check(it.nextIndex() == 0, "empty list nextIndex");

        expectIndexOutOfBounds(empty, 1);
        expectIndexOutOfBounds(empty, -1);
    }
}
