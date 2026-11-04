/*
 * @test
 * @summary Verify that the snapshot iterator of CopyOnWriteArrayList rejects remove() with UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    interface Action {
        void run();
    }

    static void expectUnsupported(String name, Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError(name + " did not throw UnsupportedOperationException");
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b"});

        Iterator<String> it = list.iterator();
        if (!it.hasNext() || !"a".equals(it.next())) {
            throw new AssertionError("unexpected first element");
        }
        expectUnsupported("iterator.remove() after next()", () -> it.remove());
        expectUnsupported("iterator.remove() before next() on fresh iterator",
                () -> list.iterator().remove());

        if (list.size() != 2 || !"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new AssertionError("list was modified by failed remove()");
        }

        ListIterator<String> li = list.listIterator();
        li.next();
        expectUnsupported("listIterator.remove()", () -> li.remove());
        expectUnsupported("listIterator.set()", () -> li.set("x"));
        expectUnsupported("listIterator.add()", () -> li.add("y"));

        if (list.size() != 2 || !"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new AssertionError("list was modified by failed iterator operations");
        }
    }
}
