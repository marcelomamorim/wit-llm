/*
 * @test
 * @summary Behavioral checks for Vector list iterator set(E) via public listIterator API
 * @run main VectorWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSetAfterNextReplacesElement();
        testSetWithoutNextThrowsIllegalState();
        testSetAfterRemoveThrowsIllegalState();
        testSetDetectsComodification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetAfterNextReplacesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        it.next();
        it.set("x");
        check("x".equals(v.get(0)), "set should replace element returned by next");
        check("b".equals(v.get(1)), "other elements must be unchanged");
        check(v.size() == 2, "size must not change on set");
    }

    static void testSetWithoutNextThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        v.add("a");
        ListIterator<String> it = v.listIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set without prior next must throw IllegalStateException");
        check("a".equals(v.get(0)), "vector must be unchanged after failed set");
    }

    static void testSetAfterRemoveThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set after remove must throw IllegalStateException");
        check(v.size() == 1 && "b".equals(v.get(0)), "vector state after remove must be [b]");
    }

    static void testSetDetectsComodification() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        it.next();
        v.add("c");
        boolean thrown = false;
        try {
            it.set("y");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "set after external structural modification must throw ConcurrentModificationException");
        check("a".equals(v.get(0)), "element must not be overwritten after comodification");
    }
}
