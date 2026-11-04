/*
 * @test
 * @summary Verifies that the iterator returned by Collections.singleton() throws UnsupportedOperationException on remove()
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsUOE();
        testRemoveAfterNextThrowsUOE();
        testRemoveDoesNotAlterSingletonContent();
    }

    static void testRemoveBeforeNextThrowsUOE() {
        Iterator<String> it = Collections.singleton("a").iterator();
        try {
            it.remove();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testRemoveAfterNextThrowsUOE() {
        Iterator<String> it = Collections.singleton("a").iterator();
        if (!it.hasNext() || !"a".equals(it.next())) {
            throw new AssertionError("Unexpected iteration result");
        }
        try {
            it.remove();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testRemoveDoesNotAlterSingletonContent() {
        java.util.Set<String> set = Collections.singleton("a");
        Iterator<String> it = set.iterator();
        it.next();
        try {
            it.remove();
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (set.size() != 1 || !set.contains("a")) {
            throw new AssertionError("Singleton content changed after failed remove()");
        }
    }
}
