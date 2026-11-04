/*
 * @test
 * @summary CopyOnWriteArrayList snapshot iterator set(E) must throw UnsupportedOperationException and leave the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testSetOnIteratorAfterNextThrowsAndListUnchanged();
        testSetOnIteratorBeforeNextThrows();
        testSetOnEmptyListIteratorThrows();
        testSetWithNullThrows();
    }

    static void testSetOnIteratorAfterNextThrowsAndListUnchanged() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        ListIterator<String> it = list.listIterator();
        it.next();
        expectUnsupported(it, "x");
        check(list.size() == 2, "size changed");
        check("a".equals(list.get(0)), "element 0 changed");
        check("b".equals(list.get(1)), "element 1 changed");
    }

    static void testSetOnIteratorBeforeNextThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a"});
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, "y");
        check(list.size() == 1, "size changed");
        check("a".equals(list.get(0)), "element changed");
    }

    static void testSetOnEmptyListIteratorThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, "z");
        check(list.isEmpty(), "list not empty after failed set");
    }

    static void testSetWithNullThrows() {
        List<String> list = new CopyOnWriteArrayList<>(new String[]{"a"});
        ListIterator<String> it = list.listIterator();
        it.next();
        expectUnsupported(it, null);
        check("a".equals(list.get(0)), "element changed after null set");
    }

    static void expectUnsupported(ListIterator<String> it, String value) {
        try {
            it.set(value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException from set(E)");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
