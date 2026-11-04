/*
 * @test
 * @summary Test Collections.EmptyListIterator.previous() throws NoSuchElementException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Obtain an empty list iterator from Collections.emptyList()
        ListIterator<Object> it = Collections.emptyList().listIterator();

        // The iterator has no previous element, so previous() must throw NoSuchElementException
        try {
            it.previous();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception caught, test passes
        }
    }
}
