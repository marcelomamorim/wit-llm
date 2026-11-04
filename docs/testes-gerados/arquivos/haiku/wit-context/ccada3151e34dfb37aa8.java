/*
 * @test
 * @summary Verify that the iterator returned by Collections.emptyIterator() throws NoSuchElementException from next() and reports no elements
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Iterator<String> strings = Collections.emptyIterator();
        checkEmpty(strings);
        checkEmpty(strings);

        Iterator<Integer> integers = Collections.emptyIterator();
        checkEmpty(integers);
    }

    private static void checkEmpty(Iterator<?> it) {
        if (it.hasNext()) {
            throw new RuntimeException("hasNext() should be false for an empty iterator");
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("next() should throw NoSuchElementException");
        }
    }
}
