/*
 * @test
 * @summary Test Collections.EmptyListIterator.add(E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Obtain an EmptyListIterator from Collections.emptyList().listIterator()
        ListIterator<Object> it = Collections.emptyList().listIterator();
        try {
            it.add(new Object());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        }
    }
}
