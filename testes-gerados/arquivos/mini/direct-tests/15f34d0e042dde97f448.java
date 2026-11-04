/*
 * @test
 * @summary Test IdentityHashMap.forEach method behavior including normal iteration and concurrent modification detection
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.BiConsumer;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testForEachNormalIteration();
        testForEachNullKeyAndValue();
        testForEachConcurrentModification();
        testForEachNullAction();
    }

    static void testForEachNormalIteration() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                sb.append(k).append("=").append(v).append(",");
            }
        });

        String result = sb.toString();
        // Because IdentityHashMap iteration order is unspecified, check both possibilities
        boolean cond1 = result.equals("key1=value1,key2=value2,");
        boolean cond2 = result.equals("key2=value2,key1=value1,");
        if (!cond1 && !cond2) {
            throw new AssertionError("Unexpected iteration result: " + result);
        }
    }

    static void testForEachNullKeyAndValue() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object nullKey = null;
        Object nullValue = null;
        Object key = new Object();
        Object value = new Object();

        map.put(nullKey, nullValue);
        map.put(key, value);

        final int[] count = {0};
        map.forEach(new BiConsumer<Object, Object>() {
            public void accept(Object k, Object v) {
                if (k == null) {
                    if (v != null) {
                        throw new AssertionError("Value for null key should be null");
                    }
                } else if (k == key) {
                    if (v != value) {
                        throw new AssertionError("Value for key object mismatch");
                    }
                } else {
                    throw new AssertionError("Unexpected key: " + k);
                }
                count[0]++;
            }
        });

        if (count[0] != 2) {
            throw new AssertionError("Expected 2 entries, got " + count[0]);
        }
    }

    static void testForEachConcurrentModification() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        try {
            map.forEach(new BiConsumer<String, String>() {
                public void accept(String k, String v) {
                    // Modify map during iteration to trigger ConcurrentModificationException
                    map.put("c", "C");
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }

    static void testForEachNullAction() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected
        }
    }
}
