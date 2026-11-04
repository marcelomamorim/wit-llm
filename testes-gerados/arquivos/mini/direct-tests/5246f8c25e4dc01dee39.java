/*
 * @test
 * @summary Test java.util.Vector set(E e) method behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest {
    public static void main(String[] args) {
        testSetWithoutNextThrows();
        testSetAfterNextUpdatesElement();
    }

    private static void testSetWithoutNextThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        ListIterator<String> it = v.listIterator();
        try {
            it.set("b");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testSetAfterNextUpdatesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        ListIterator<String> it = v.listIterator();
        String next = it.next();
        if (!"a".equals(next)) {
            throw new RuntimeException("Unexpected element returned by next()");
        }
        it.set("b");
        if (!"b".equals(v.get(0))) {
            throw new RuntimeException("Element not updated by set()");
        }
    }
}
