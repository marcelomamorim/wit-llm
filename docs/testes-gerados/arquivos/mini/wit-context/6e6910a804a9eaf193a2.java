/*
 * @test
 * @summary Test Vector.Itr.remove() method for expected IllegalStateException and normal removal
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.Iterator;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextSucceeds();
    }

    private static void testRemoveWithoutNextThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        Iterator<String> it = v.iterator();
        try {
            // Directly call remove() without next() should throw IllegalStateException
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testRemoveAfterNextSucceeds() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Iterator<String> it = v.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first element to be 'a'");
        }
        it.remove(); // should succeed without exception
        if (v.size() != 1 || !"b".equals(v.get(0))) {
            throw new RuntimeException("Element was not removed correctly");
        }
        // Calling remove() again without next() should throw IllegalStateException
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown on second remove call");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
