/*
 * @test
 * @summary Verify that COWIterator.set(E) always throws UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testSetOnListIteratorThrows();
        testSetOnListIteratorWithIndexThrows();
        testSetWithNullThrows();
        testSetOnEmptyListThrows();
        testSetAfterNextThrowsAndListUnchanged();
        System.out.println("PASSED");
    }

    private static void testSetOnListIteratorThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, "x");
        check(list.size() == 2, "list size changed");
        check("a".equals(list.get(0)) && "b".equals(list.get(1)), "list contents changed");
    }

    private static void testSetOnListIteratorWithIndexThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        ListIterator<String> it = list.listIterator(1);
        expectUnsupported(it, "y");
        check("b".equals(list.get(1)), "element at index 1 changed");
    }

    private static void testSetWithNullThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a"});
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, null);
        check("a".equals(list.get(0)), "list contents changed");
    }

    private static void testSetOnEmptyListThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ListIterator<String> it = list.listIterator();
        expectUnsupported(it, "z");
        check(list.isEmpty(), "empty list became non-empty");
    }

    private static void testSetAfterNextThrowsAndListUnchanged() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        ListIterator<String> it = list.listIterator();
        it.next();
        expectUnsupported(it, "w");
        check("a".equals(list.get(0)) && "b".equals(list.get(1)), "list contents changed after next()");
    }

    private static void expectUnsupported(ListIterator<String> it, String value) {
        try {
            it.set(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException from set()");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
