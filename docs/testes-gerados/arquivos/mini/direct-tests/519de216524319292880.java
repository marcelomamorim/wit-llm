/*
 * @test
 * @summary Test ConcurrentHashMap tryAdvance method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceReturnsFalseWhenNoNext();
        testTryAdvanceAcceptsKeyWhenNextExists();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentHashMap.KeySetView<String, Boolean> keySet = ConcurrentHashMap.newKeySet();
        try {
            keySet.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceReturnsFalseWhenNoNext() {
        ConcurrentHashMap.KeySetView<String, Boolean> keySet = ConcurrentHashMap.newKeySet();
        // empty set, so advance() returns null internally
        boolean result = keySet.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called when no next element");
            }
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false when no next element");
        }
    }

    static void testTryAdvanceAcceptsKeyWhenNextExists() {
        ConcurrentHashMap.KeySetView<String, Boolean> keySet = ConcurrentHashMap.newKeySet();
        keySet.add("testKey");
        final boolean[] accepted = {false};
        boolean result = keySet.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                if ("testKey".equals(s)) {
                    accepted[0] = true;
                } else {
                    throw new AssertionError("Unexpected key: " + s);
                }
            }
        });
        if (!result) {
            throw new AssertionError("tryAdvance should return true when next element exists");
        }
        if (!accepted[0]) {
            throw new AssertionError("Consumer accept method was not called with expected key");
        }
    }
}
