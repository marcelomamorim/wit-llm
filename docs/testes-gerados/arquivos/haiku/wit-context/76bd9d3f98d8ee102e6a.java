/*
 * @test
 * @summary Verifies that the iterator returned by Collections.singleton rejects remove() with UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Iterator<String> it = Collections.singleton("x").iterator();
        if (!it.hasNext()) {
            throw new AssertionError("singleton iterator should have an element");
        }
        if (!"x".equals(it.next())) {
            throw new AssertionError("unexpected element from singleton iterator");
        }
        try {
            it.remove();
            throw new AssertionError("remove() after next() should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        Iterator<String> fresh = Collections.singleton("y").iterator();
        try {
            fresh.remove();
            throw new AssertionError("remove() before next() should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
