/*
 * @test
 * @summary Verifies that the iterator next() method of WeakHashMap returns the live entries with correct keys and values
 * @run main WeakHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsAllEntries();
        testNextReturnsNullKeyEntry();
        testNextReturnsNullValueEntry();
        testNextEntryConsistentWithGet();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsAllEntries() {
        String k1 = new String("a");
        String k2 = new String("b");
        String k3 = new String("c");
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put(k1, 1);
        map.put(k2, 2);
        map.put(k3, 3);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        expected.put("b", 2);
        expected.put("c", 3);

        Map<String, Integer> seen = new HashMap<>();
        int count = 0;
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            seen.put(e.getKey(), e.getValue());
            count++;
        }
        check(count == 3, "expected 3 entries from next(), got " + count);
        check(seen.equals(expected), "entries returned by next() do not match: " + seen);
        check(!it.hasNext(), "iterator should be exhausted");
        check(k1.length() == 1 && k2.length() == 1 && k3.length() == 1, "keys kept reachable");
    }

    static void testNextReturnsNullKeyEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(null, "nullKeyValue");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        check(it.hasNext(), "iterator should have one element");
        Map.Entry<String, String> e = it.next();
        check(e.getKey() == null, "next() should return null key");
        check("nullKeyValue".equals(e.getValue()), "unexpected value for null key: " + e.getValue());
        check(!it.hasNext(), "iterator should be exhausted after single entry");
    }

    static void testNextReturnsNullValueEntry() {
        String key = new String("k");
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(key, null);

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> e = it.next();
        check("k".equals(e.getKey()), "unexpected key: " + e.getKey());
        check(e.getValue() == null, "next() should return null value");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNextEntryConsistentWithGet() {
        String k1 = new String("x");
        String k2 = new String("y");
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(k1, "v1");
        map.put(k2, "v2");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            check(map.containsKey(e.getKey()), "returned key not present in map");
            check(e.getValue().equals(map.get(e.getKey())),
                  "entry value differs from get(): " + e.getValue());
            count++;
        }
        check(count == 2, "expected 2 entries, got " + count);
    }
}
