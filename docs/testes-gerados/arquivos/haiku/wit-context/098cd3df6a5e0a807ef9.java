/*
 * @test
 * @summary Verifies ListIterator.set behavior of ArrayList.subList iterators: IllegalStateException without prior next, update of the root list after next, and ConcurrentModificationException after structural modification of the root list
 * @run main ArrayListWitupTest
 */
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSetWithoutNextThrowsIllegalState();
        testSetAfterNextUpdatesRoot();
        testSetAfterStructuralModificationThrowsCME();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetWithoutNextThrowsIllegalState() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        List<String> sub = root.subList(0, 2);
        ListIterator<String> it = sub.listIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException when set is called before next");
        check("a".equals(root.get(0)), "root must not change");
    }

    static void testSetAfterNextUpdatesRoot() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        root.add("c");
        List<String> sub = root.subList(1, 3);
        ListIterator<String> it = sub.listIterator();
        check("b".equals(it.next()), "next should return first sublist element");
        it.set("x");
        check("x".equals(root.get(1)), "set should update root at offset + lastRet");
        check("c".equals(root.get(2)), "other elements must not change");
    }

    static void testSetAfterStructuralModificationThrowsCME() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        List<String> sub = root.subList(0, 2);
        ListIterator<String> it = sub.listIterator();
        it.next();
        root.add("c");
        boolean thrown = false;
        try {
            it.set("z");
        } catch (ConcurrentModificationException expected) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification of root");
    }
}
