/*
 * @test
 * @summary Verifies that ListIterator.add on the empty list iterator returned by Collections.emptyListIterator() throws UnsupportedOperationException and leaves the iterator empty.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        ListIterator<String> it = Collections.emptyListIterator();

        boolean thrown = false;
        try {
            it.add("x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add(E) did not throw UnsupportedOperationException");
        }

        boolean thrownNull = false;
        try {
            it.add(null);
        } catch (UnsupportedOperationException e) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new RuntimeException("add(null) did not throw UnsupportedOperationException");
        }

        if (it.hasNext() || it.hasPrevious()) {
            throw new RuntimeException("empty list iterator changed state after failed add");
        }
    }
}
