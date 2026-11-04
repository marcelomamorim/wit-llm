/*
 * @test
 * @summary Verifies that set(E) on the empty list iterator throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSetWithStringThrowsUOE();
        testSetWithNullThrowsUOE();
        testIteratorIsEmpty();
    }

    static void testSetWithStringThrowsUOE() {
        ListIterator<String> it = Collections.emptyListIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("set(E) did not throw UnsupportedOperationException");
        }
    }

    static void testSetWithNullThrowsUOE() {
        ListIterator<String> it = Collections.emptyListIterator();
        boolean thrown = false;
        try {
            it.set(null);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("set(null) did not throw UnsupportedOperationException");
        }
    }

    static void testIteratorIsEmpty() {
        ListIterator<String> it = Collections.emptyListIterator();
        if (it.hasNext()) {
            throw new AssertionError("empty list iterator should not have next elements");
        }
        if (it.hasPrevious()) {
            throw new AssertionError("empty list iterator should not have previous elements");
        }
    }
}
