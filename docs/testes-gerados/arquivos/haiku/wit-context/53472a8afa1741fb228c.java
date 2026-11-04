/*
 * @test
 * @summary Verifies that previous() on the empty ListIterator returned by Collections.emptyListIterator() throws NoSuchElementException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        ListIterator<String> it = Collections.emptyListIterator();

        if (it.hasPrevious()) {
            throw new RuntimeException("hasPrevious() should be false for an empty iterator");
        }
        if (it.nextIndex() != 0) {
            throw new RuntimeException("nextIndex() should be 0, was " + it.nextIndex());
        }
        if (it.previousIndex() != -1) {
            throw new RuntimeException("previousIndex() should be -1, was " + it.previousIndex());
        }

        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                it.previous();
            } catch (NoSuchElementException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("previous() should throw NoSuchElementException (attempt " + i + ")");
            }
        }
    }
}
