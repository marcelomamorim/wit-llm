/*
 * @test
 * @summary CopyOnWriteArrayList snapshot iterator set(E) must throw UnsupportedOperationException and leave the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void expectUnsupported(Runnable r, String what) {
        try {
            r.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException for " + what);
    }

    static void checkContents(CopyOnWriteArrayList<String> list, String... expected) {
        if (list.size() != expected.length) {
            throw new AssertionError("Unexpected size: " + list.size());
        }
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(list.get(i))) {
                throw new AssertionError("Unexpected element at " + i + ": " + list.get(i));
            }
        }
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        ListIterator<String> it = list.listIterator();
        it.next();
        expectUnsupported(() -> it.set("x"), "set on non-empty snapshot iterator");
        checkContents(list, "a", "b");

        ListIterator<String> it2 = list.listIterator();
        expectUnsupported(() -> it2.set(null), "set(null) before next()");
        checkContents(list, "a", "b");

        CopyOnWriteArrayList<String> empty = new CopyOnWriteArrayList<>();
        ListIterator<String> it3 = empty.listIterator();
        expectUnsupported(() -> it3.set("y"), "set on empty list iterator");
        checkContents(empty);
    }
}
