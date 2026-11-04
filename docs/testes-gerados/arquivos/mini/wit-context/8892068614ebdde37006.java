/*
 * @test
 * @summary Test Collections.EmptyListIterator.set(E) throws IllegalStateException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Obtain an EmptyListIterator from Collections.emptyList().listIterator()
        ListIterator<Object> it = Collections.emptyList().listIterator();
        try {
            it.set(new Object());
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
