/*
 * @test
 * @summary Verify that the iterator returned by Collections.singleton rejects remove() with UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveAfterNextThrows();
        testRemoveBeforeNextThrows();
        testIteratorReturnsElementBeforeRemove();
    }

    static void testRemoveAfterNextThrows() {
        Iterator<String> it = Collections.singleton("a").iterator();
        String value = it.next();
        if (!"a".equals(value)) {
            throw new AssertionError("Unexpected element: " + value);
        }
        try {
            it.remove();
            throw new AssertionError("remove() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testRemoveBeforeNextThrows() {
        Iterator<String> it = Collections.singleton("b").iterator();
        try {
            it.remove();
            throw new AssertionError("remove() before next() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testIteratorReturnsElementBeforeRemove() {
        Iterator<String> it = Collections.singleton("c").iterator();
        if (!it.hasNext()) {
            throw new AssertionError("hasNext() returned false for non-empty singleton");
        }
        if (!"c".equals(it.next())) {
            throw new AssertionError("next() returned unexpected element");
        }
        if (it.hasNext()) {
            throw new AssertionError("hasNext() returned true after the only element");
        }
    }
}
