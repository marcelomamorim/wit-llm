/*
 * @test
 * @summary Verifies that the empty iterator returned by Collections.emptyIterator() rejects a null action in forEachRemaining and never invokes a non-null action.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testNonNullActionIsNeverInvoked();
        testEmptyIteratorHasNoElements();
    }

    static void testNullActionThrowsNullPointerException() {
        Iterator<String> it = Collections.emptyIterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testNonNullActionIsNeverInvoked() {
        Iterator<String> it = Collections.emptyIterator();
        final int[] calls = {0};
        Consumer<String> action = s -> calls[0]++;
        it.forEachRemaining(action);
        if (calls[0] != 0) {
            throw new RuntimeException("Action should not be invoked, calls=" + calls[0]);
        }
    }

    static void testEmptyIteratorHasNoElements() {
        Iterator<String> it = Collections.emptyIterator();
        if (it.hasNext()) {
            throw new RuntimeException("Empty iterator should not have next element");
        }
    }
}
