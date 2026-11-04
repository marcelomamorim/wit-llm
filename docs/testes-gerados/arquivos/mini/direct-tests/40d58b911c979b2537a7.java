/*
 * @test
 * @summary Test java.util.WeakHashMap.forEach method behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testForEachBasic();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachBasic() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put(null, "nullValue");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                sb.append(k).append("=").append(v).append(",");
            }
        });

        String result = sb.toString();
        // The result should contain all three entries (including null key)
        if (!result.contains("key1=value1") || !result.contains("key2=value2") || !result.contains("null=nullValue")) {
            throw new AssertionError("forEach did not visit all entries: " + result);
        }
    }

    static void testForEachNullAction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        try {
            map.forEach(new BiConsumer<String, String>() {
                boolean first = true;
                public void accept(String k, String v) {
                    if (first) {
                        first = false;
                        map.put("key2", "value2"); // structural modification during iteration
                    }
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
