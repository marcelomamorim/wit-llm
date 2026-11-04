/*
 * @test
 * @summary Verify that the empty iterator returned by Collections.emptyIterator() rejects a null action in forEachRemaining and does nothing for a non-null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNonNullActionIsNotInvoked();
    }

    static void testNullActionThrowsNPE() {
        Iterator<String> it = Collections.emptyIterator();
        boolean threw = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("expected NullPointerException for null action");
        }
    }

    static void testNonNullActionIsNotInvoked() {
        Iterator<String> it = Collections.emptyIterator();
        int[] calls = new int[1];
        Consumer<String> action = s -> calls[0]++;
        it.forEachRemaining(action);
        if (calls[0] != 0) {
            throw new AssertionError("action should not be invoked on empty iterator");
        }
        if (it.hasNext()) {
            throw new AssertionError("empty iterator should have no next element");
        }
    }
}
