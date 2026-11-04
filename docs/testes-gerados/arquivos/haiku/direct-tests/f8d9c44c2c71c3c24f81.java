/*
 * @test
 * @summary Verify that ListIterator.add on a CopyOnWriteArrayList iterator throws UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testAddOnNonEmptyList();
        testAddNullOnEmptyList();
        testIteratorStillUsableAfterFailedAdd();
    }

    static void testAddOnNonEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, "b");
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new AssertionError("list changed after failed add");
        }
    }

    static void testAddNullOnEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, null);
        if (!list.isEmpty()) {
            throw new AssertionError("list not empty after failed add of null");
        }
    }

    static void testIteratorStillUsableAfterFailedAdd() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, "z");
        if (!it.hasNext() || !"x".equals(it.next())) {
            throw new AssertionError("iterator state broken after failed add");
        }
    }

    static void expectUnsupported(ListIterator<String> it, String value) {
        try {
            it.add(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException");
    }
}
