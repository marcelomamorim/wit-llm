/*
 * @test
 * @summary Verify that COWIterator.remove() always throws UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testIteratorRemoveBeforeNextThrows();
        testIteratorRemoveAfterNextThrows();
        testListIteratorRemoveThrows();
        testIteratorRemoveDoesNotModifyList();
    }

    static void testIteratorRemoveBeforeNextThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        Iterator<String> it = list.iterator();
        expectUnsupported(it, "iterator.remove() before next()");
    }

    static void testIteratorRemoveAfterNextThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        Iterator<String> it = list.iterator();
        if (!"a".equals(it.next())) {
            throw new RuntimeException("Unexpected first element");
        }
        expectUnsupported(it, "iterator.remove() after next()");
    }

    static void testListIteratorRemoveThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"x", "y"});
        ListIterator<String> it = list.listIterator();
        it.next();
        expectUnsupported(it, "listIterator.remove()");
    }

    static void testIteratorRemoveDoesNotModifyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        Iterator<String> it = list.iterator();
        it.next();
        try {
            it.remove();
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (list.size() != 3) {
            throw new RuntimeException("List size changed: " + list.size());
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1)) || !"c".equals(list.get(2))) {
            throw new RuntimeException("List contents changed: " + list);
        }
    }

    static void expectUnsupported(Iterator<String> it, String what) {
        try {
            it.remove();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for " + what);
    }
}
