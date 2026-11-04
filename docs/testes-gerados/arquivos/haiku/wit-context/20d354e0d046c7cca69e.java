/*
 * @test
 * @summary Witup-derived tests for CopyOnWriteArrayList snapshot iterator next()
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyIteratorNextThrows();
        testExhaustedIteratorNextThrowsRepeatedly();
        testSnapshotIteratorIgnoresLaterAdds();
    }

    static void testEmptyIteratorNextThrows() {
        Iterator<String> it = new CopyOnWriteArrayList<String>().iterator();
        if (it.hasNext()) {
            throw new RuntimeException("hasNext() should be false for empty list");
        }
        expectNoSuchElement(it);
    }

    static void testExhaustedIteratorNextThrowsRepeatedly() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        Iterator<String> it = list.iterator();
        check("a", it.next());
        check("b", it.next());
        expectNoSuchElement(it);
        expectNoSuchElement(it);
    }

    static void testSnapshotIteratorIgnoresLaterAdds() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(Arrays.asList("x"));
        Iterator<String> it = list.iterator();
        list.add("y");
        check("x", it.next());
        expectNoSuchElement(it);
    }

    static void expectNoSuchElement(Iterator<String> it) {
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException");
    }

    static void check(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
