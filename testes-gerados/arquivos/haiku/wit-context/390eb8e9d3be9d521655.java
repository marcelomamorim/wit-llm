/*
 * @test
 * @summary Verifies that the snapshot ListIterator of CopyOnWriteArrayList rejects add with UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testIteratorAddThrowsOnNonEmptyList();
        testIteratorAddThrowsOnEmptyListWithNull();
    }

    static void testIteratorAddThrowsOnNonEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        ListIterator<String> it = list.listIterator();
        expectUnsupported(() -> it.add("x"));
        if (list.size() != 2) {
            throw new AssertionError("size changed after failed iterator add: " + list.size());
        }
        if (!list.toString().equals("[a, b]")) {
            throw new AssertionError("list changed after failed iterator add: " + list);
        }
    }

    static void testIteratorAddThrowsOnEmptyListWithNull() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        ListIterator<Integer> it = list.listIterator();
        expectUnsupported(() -> it.add(null));
        if (!list.isEmpty()) {
            throw new AssertionError("list not empty after failed iterator add: " + list);
        }
    }

    static void expectUnsupported(Runnable action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException");
    }
}
