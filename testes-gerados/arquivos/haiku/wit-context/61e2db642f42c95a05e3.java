/*
 * @test
 * @summary CopyOnWriteArrayList snapshot iterators must reject set(E) with UnsupportedOperationException and leave the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});

        checkSetThrows(list.iterator(), "iterator");
        checkSetThrows(list.listIterator(), "listIterator");
        checkSetThrows(list.listIterator(1), "listIterator(1)");

        ListIterator<String> advanced = list.listIterator();
        advanced.next();
        checkSetThrows(advanced, "listIterator after next");

        CopyOnWriteArrayList<String> empty = new CopyOnWriteArrayList<>();
        checkSetThrows(empty.listIterator(), "empty listIterator");

        if (list.size() != 3 || !"a".equals(list.get(0))
                || !"b".equals(list.get(1)) || !"c".equals(list.get(2))) {
            throw new RuntimeException("list was modified by a rejected set");
        }
        if (empty.size() != 0) {
            throw new RuntimeException("empty list was modified by a rejected set");
        }
    }

    private static void checkSetThrows(ListIterator<String> it, String label) {
        try {
            it.set("x");
            throw new RuntimeException(label + ": set(E) did not throw");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
