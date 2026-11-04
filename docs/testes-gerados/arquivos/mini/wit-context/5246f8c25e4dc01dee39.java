/*
 * @test
 * @summary Test fail-fast behavior of Vector.ListItr.set(E) method
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest {
    public static void main(String[] args) {
        testSetThrowsIllegalStateException();
        testSetUpdatesElement();
    }

    private static void testSetThrowsIllegalStateException() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        ListIterator<String> it = vector.listIterator();
        // lastRet == -1 before next() or previous() called
        boolean thrown = false;
        try {
            it.set("b");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when calling set() before next()/previous()");
        }
    }

    private static void testSetUpdatesElement() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        ListIterator<String> it = vector.listIterator();
        it.next(); // advances lastRet to 0
        it.set("b");
        if (!"b".equals(vector.get(0))) {
            throw new RuntimeException("set() did not update the element correctly");
        }
    }
}
