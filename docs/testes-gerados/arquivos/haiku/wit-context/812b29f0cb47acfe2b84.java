/*
 * @test
 * @summary Verifies that the iterator returned by Collections.emptyIterator() throws IllegalStateException from remove() when no element has been returned
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Iterator<Object> it = Collections.emptyIterator();
        if (it.hasNext()) {
            throw new RuntimeException("emptyIterator must have no elements");
        }
        try {
            it.remove();
            throw new RuntimeException("remove must throw IllegalStateException on first call");
        } catch (IllegalStateException expected) {
        }
        try {
            it.remove();
            throw new RuntimeException("remove must throw IllegalStateException on repeated call");
        } catch (IllegalStateException expected) {
        }
    }
}
