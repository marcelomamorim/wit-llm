/*
 * @test
 * @summary Test java.util.HashMap tryAdvance method behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.HashMap.Node;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {

    static class KeyConsumer<K> implements Consumer<K> {
        K lastKey = null;
        int count = 0;
        public void accept(K k) {
            lastKey = k;
            count++;
        }
    }

    public static void main(String[] args) {
        testNullActionThrows();
        testTryAdvanceEmptyMapReturnsFalse();
        testTryAdvanceIteratesKeys();
        testTryAdvanceConcurrentModificationThrows();
    }

    // Test that tryAdvance throws NullPointerException if action is null
    private static void testNullActionThrows() {
        HashMap<String, String> map = new HashMap<>();
        java.util.Spliterator<String> spliterator = map.keySet().spliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    // Test that tryAdvance returns false on empty map
    private static void testTryAdvanceEmptyMapReturnsFalse() {
        HashMap<String, String> map = new HashMap<>();
        java.util.Spliterator<String> spliterator = map.keySet().spliterator();
        boolean result = spliterator.tryAdvance(k -> {
            throw new RuntimeException("Should not be called on empty map");
        });
        if (result) {
            throw new RuntimeException("tryAdvance should return false on empty map");
        }
    }

    // Test that tryAdvance iterates keys and returns true until exhausted
    private static void testTryAdvanceIteratesKeys() {
        HashMap<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        java.util.Spliterator<String> spliterator = map.keySet().spliterator();
        KeyConsumer<String> consumer = new KeyConsumer<>();

        int count = 0;
        while (spliterator.tryAdvance(consumer)) {
            count++;
            if (consumer.lastKey == null) {
                throw new RuntimeException("Consumer did not receive a key");
            }
        }
        if (count != map.size()) {
            throw new RuntimeException("tryAdvance did not iterate all keys: expected " + map.size() + " but got " + count);
        }
    }

    // Test that tryAdvance throws ConcurrentModificationException if map modified during iteration
    private static void testTryAdvanceConcurrentModificationThrows() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        java.util.Spliterator<String> spliterator = map.keySet().spliterator();

        // Advance once successfully
        boolean advanced = spliterator.tryAdvance(k -> {});
        if (!advanced) {
            throw new RuntimeException("Expected to advance at least once");
        }

        // Modify map to cause modCount mismatch
        map.put("c", "C");

        try {
            spliterator.tryAdvance(k -> {});
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
