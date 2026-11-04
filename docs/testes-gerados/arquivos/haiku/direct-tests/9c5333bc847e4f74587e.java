/*
 * @test
 * @summary Behavioral checks for WeakHashMap(int initialCapacity) using only public observable API
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testZeroCapacityMapIsUsable();
        testSmallCapacityBasicOperations();
        testNullKeyAndNullValueSupported();
        testManyEntriesWithSmallInitialCapacity();
    }

    static void testZeroCapacityMapIsUsable() {
        WeakHashMap<String, String> m = new WeakHashMap<>(0);
        check(m.isEmpty(), "new map with capacity 0 should be empty");
        check(m.size() == 0, "size of new map should be 0");
        String key = new String("zero-key");
        m.put(key, "value");
        check(m.size() == 1, "size should be 1 after one put");
        check("value".equals(m.get(key)), "get should return stored value");
        check(m.containsKey(key), "containsKey should be true for stored key");
    }

    static void testSmallCapacityBasicOperations() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>(16);
        check(m.isEmpty(), "new map should be empty");
        String a = new String("a");
        String b = new String("b");
        m.put(a, 1);
        m.put(b, 2);
        check(m.size() == 2, "size should be 2");
        check(m.get(a) == 1, "get(a) should be 1");
        check(m.get(b) == 2, "get(b) should be 2");
        m.put(a, 3);
        check(m.size() == 2, "overwriting key should not change size");
        check(m.get(a) == 3, "get(a) should reflect overwritten value");
        check(m.remove(b) == 2, "remove should return previous value");
        check(m.size() == 1, "size should be 1 after remove");
        check(!m.containsKey(b), "removed key should not be present");
    }

    static void testNullKeyAndNullValueSupported() {
        WeakHashMap<String, String> m = new WeakHashMap<>(4);
        m.put(null, "nullKeyValue");
        check("nullKeyValue".equals(m.get(null)), "null key should be supported");
        check(m.containsKey(null), "containsKey(null) should be true");
        String key = new String("nullValueKey");
        m.put(key, null);
        check(m.containsKey(key), "key with null value should be present");
        check(m.get(key) == null, "get should return null for null value");
        check(m.size() == 2, "size should count null-valued entries");
    }

    static void testManyEntriesWithSmallInitialCapacity() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>(1);
        List<String> keys = new ArrayList<>();
        final int n = 100;
        for (int i = 0; i < n; i++) {
            String k = new String("key" + i);
            keys.add(k);
            m.put(k, i);
        }
        check(m.size() == n, "size should be " + n + " but was " + m.size());
        for (int i = 0; i < n; i++) {
            Integer v = m.get(keys.get(i));
            check(v != null && v == i, "wrong value for key" + i);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
