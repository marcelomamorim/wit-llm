/*
 * @test
 * @summary Test Collections.EmptyIterator.next() throws NoSuchElementException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Iterator<Object> it = Collections.emptyIterator();
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
