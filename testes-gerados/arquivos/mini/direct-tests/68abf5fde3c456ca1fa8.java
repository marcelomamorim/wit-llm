/*
 * @test
 * @summary Test java.util.WeakHashMap.tryAdvance(Consumer) behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceEmptyMap();
        testTryAdvanceWithEntries();
        testTryAdvanceConcurrentModification();
    }

    static void testTryAdvanceNullAction() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyMap() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        boolean advanced = map.tryAdvance(new Consumer<Object>() {
            public void accept(Object o) {
                throw new Error("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new Error("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceWithEntries() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        boolean advanced1 = map.tryAdvance(consumer);
        boolean advanced2 = map.tryAdvance(consumer);
        boolean advanced3 = map.tryAdvance(consumer);

        if (!advanced1 || !advanced2) {
            throw new Error("tryAdvance should return true while entries remain");
        }
        if (advanced3) {
            throw new Error("tryAdvance should return false after all entries processed");
        }

        String result = sb.toString();
        if (!(result.contains("key1") && result.contains("key2"))) {
            throw new Error("Consumer did not receive expected keys: " + result);
        }
    }

    static void testTryAdvanceConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        Consumer<String> consumer = new Consumer<String>() {
            boolean first = true;
            public void accept(String s) {
                if (first) {
                    first = false;
                    // Modify map during iteration to trigger ConcurrentModificationException
                    map.put("key2", "value2");
                }
            }
        };

        try {
            while (map.tryAdvance(consumer)) {
                // keep advancing
            }
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
