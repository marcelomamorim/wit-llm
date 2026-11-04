/*
 * @test
 * @summary Test ConcurrentHashMap.KeySpliterator.tryAdvance behavior including NPE on null action
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceReturnsFalseWhenNoMoreElements();
        testTryAdvanceAcceptsKey();
    }

    static void testTryAdvanceNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        ConcurrentHashMap.KeySpliterator<String, String> spliterator = map.keySpliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceReturnsFalseWhenNoMoreElements() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ConcurrentHashMap.KeySpliterator<String, String> spliterator = map.keySpliterator();
        boolean result = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String t) {
                throw new RuntimeException("Should not be called");
            }
        });
        if (result) {
            throw new RuntimeException("tryAdvance should return false when no elements");
        }
    }

    static void testTryAdvanceAcceptsKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        ConcurrentHashMap.KeySpliterator<String, String> spliterator = map.keySpliterator();
        final boolean[] called = {false};
        boolean result = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String t) {
                if (!"foo".equals(t)) {
                    throw new RuntimeException("Unexpected key: " + t);
                }
                called[0] = true;
            }
        });
        if (!result) {
            throw new RuntimeException("tryAdvance should return true when element present");
        }
        if (!called[0]) {
            throw new RuntimeException("Consumer.accept was not called");
        }
    }
}
