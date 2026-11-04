/*
 * @test
 * @summary Verifies observable behavior of entries returned by IdentityHashMap.entrySet() iteration, including getKey() for regular, identity-distinct and null keys
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testGetKeyReturnsIdentityKeys();
        testGetKeyReturnsNullKey();
        testEntryKeyConsistentWithGet();
    }

    static void testGetKeyReturnsIdentityKeys() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object k1 = new String("a");
        Object k2 = new String("a");
        map.put(k1, "v1");
        map.put(k2, "v2");

        int count = 0;
        boolean sawK1 = false;
        boolean sawK2 = false;
        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        for (Map.Entry<Object, Object> e : entries) {
            count++;
            Object k = e.getKey();
            if (k == k1) {
                sawK1 = true;
            } else if (k == k2) {
                sawK2 = true;
            } else {
                throw new RuntimeException("getKey returned an unexpected key");
            }
        }
        check(count == 2, "expected 2 entries, got " + count);
        check(sawK1, "getKey did not return first identity key");
        check(sawK2, "getKey did not return second identity key");
    }

    static void testGetKeyReturnsNullKey() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        map.put(null, "nullValue");
        Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
        check(it.hasNext(), "iterator should have one entry");
        Map.Entry<Object, Object> e = it.next();
        check(e.getKey() == null, "getKey should return null for null key");
        check("nullValue".equals(e.getValue()), "getValue mismatch for null key");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testEntryKeyConsistentWithGet() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object k1 = new Object();
        Object k2 = new Object();
        map.put(k1, "one");
        map.put(k2, null);
        map.put(null, "zero");

        for (Map.Entry<Object, Object> e : map.entrySet()) {
            Object k = e.getKey();
            check(map.containsKey(k), "containsKey false for key returned by getKey");
            check(map.get(k) == e.getValue(), "get(getKey()) differs from getValue()");
        }
        check(map.size() == 3, "unexpected size");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
