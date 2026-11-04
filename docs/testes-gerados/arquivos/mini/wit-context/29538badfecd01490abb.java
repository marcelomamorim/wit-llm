/*
 * @test
 * @summary Test WeakHashMap.tryAdvance method behavior including null action and normal iteration
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.AbstractMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceEmptyMap();
        testTryAdvanceWithEntries();
        testTryAdvanceConcurrentModification();
    }

    private static void testTryAdvanceNullAction() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        try {
            // Passing null action should throw NullPointerException
            map.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        // tryAdvance on empty map should return false
        boolean result = map.tryAdvance(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                throw new AssertionError("Should not be called on empty map");
            }
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty map");
        }
    }

    private static void testTryAdvanceWithEntries() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final int[] count = {0};
        boolean advanced = map.tryAdvance(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                if (e.getKey() == null || e.getValue() == null) {
                    throw new AssertionError("Entry key or value should not be null");
                }
                count[0]++;
            }
        });

        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when entries exist");
        }
        if (count[0] != 1) {
            throw new AssertionError("Consumer should be called exactly once per tryAdvance call");
        }
    }

    private static void testTryAdvanceConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        Consumer<Map.Entry<String, String>> consumer = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                // Modify map during iteration to trigger ConcurrentModificationException
                map.put("anotherKey", "anotherValue");
            }
        };

        try {
            map.tryAdvance(consumer);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during tryAdvance");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
