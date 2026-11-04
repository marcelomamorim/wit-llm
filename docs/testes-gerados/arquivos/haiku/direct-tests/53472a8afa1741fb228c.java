/*
 * @test
 * @summary Verifies that previous() on the empty list iterator always throws NoSuchElementException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        ListIterator<String> it = Collections.emptyListIterator();

        if (it.hasPrevious()) {
            throw new RuntimeException("hasPrevious() should be false for empty iterator");
        }

        checkPreviousThrows(it, 1);
        checkPreviousThrows(it, 2);
    }

    private static void checkPreviousThrows(ListIterator<String> it, int attempt) {
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("previous() did not throw NoSuchElementException, attempt " + attempt);
        }
    }
}
