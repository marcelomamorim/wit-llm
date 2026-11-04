/*
 * @test
 * @summary Verifies that the ListIterator returned by Collections.emptyListIterator() rejects set() with IllegalStateException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        ListIterator<String> it = Collections.emptyListIterator();

        if (it.hasNext()) {
            throw new RuntimeException("emptyListIterator should have no elements");
        }
        if (it.hasPrevious()) {
            throw new RuntimeException("emptyListIterator should have no previous elements");
        }

        try {
            it.set("x");
            throw new RuntimeException("expected IllegalStateException from set(E)");
        } catch (IllegalStateException expected) {
            // expected
        }

        try {
            it.set(null);
            throw new RuntimeException("expected IllegalStateException from set(null)");
        } catch (IllegalStateException expected) {
            // expected
        }

        if (it.hasNext() || it.hasPrevious()) {
            throw new RuntimeException("iterator state must remain empty after failed set");
        }
    }
}
