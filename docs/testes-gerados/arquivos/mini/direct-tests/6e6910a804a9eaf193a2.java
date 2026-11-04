/*
 * @test
 * @summary Test Vector remove() method behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
    }

    private static void testRemoveWithoutNextThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        ListIterator<String> it = v.listIterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testRemoveAfterNextRemovesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first element 'a', got: " + first);
        }
        it.remove();
        if (v.size() != 1) {
            throw new RuntimeException("Vector size should be 1 after remove, got: " + v.size());
        }
        if (!"b".equals(v.get(0))) {
            throw new RuntimeException("Expected remaining element 'b', got: " + v.get(0));
        }
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove call not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
