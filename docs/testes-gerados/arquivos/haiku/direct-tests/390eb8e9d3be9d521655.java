/*
 * @test
 * @summary Verifies that ListIterator.add on a CopyOnWriteArrayList iterator throws UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void expectUnsupported(Runnable r, String what) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException: " + what);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b"});

        ListIterator<String> it = list.listIterator();
        expectUnsupported(() -> it.add("x"), "listIterator().add(non-null)");
        expectUnsupported(() -> it.add(null), "listIterator().add(null)");

        ListIterator<String> it2 = list.listIterator(1);
        expectUnsupported(() -> it2.add("y"), "listIterator(1).add");

        List<String> snapshot = list;
        check(snapshot.size() == 2, "list size changed after failed add");
        check("a".equals(snapshot.get(0)) && "b".equals(snapshot.get(1)),
              "list contents changed after failed add");
    }
}
