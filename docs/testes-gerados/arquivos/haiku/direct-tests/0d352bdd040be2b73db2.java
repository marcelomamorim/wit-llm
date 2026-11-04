/*
 * @test
 * @summary Verifies that the public no-arg WeakHashMap constructor creates an empty, usable map
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        check(m.isEmpty(), "new map should be empty");
        check(m.size() == 0, "new map size should be 0");
        check(m.get("a") == null, "get on empty map should return null");
        check(!m.containsKey("a"), "containsKey on empty map should be false");

        String k = new String("k");
        m.put(k, 1);
        check(m.size() == 1, "size should be 1 after one put");
        check(m.get(k) == 1, "value should be retrievable by the strongly referenced key");
        check(!m.isEmpty(), "map should not be empty after put");

        m.put(null, 2);
        check(m.containsKey(null), "null key should be supported");
        check(m.get(null) == 2, "value for null key should be retrievable");

        m.put("v", null);
        check(m.containsKey("v"), "null value should be stored with its key");
        check(m.get("v") == null, "null value should be returned as null");

        m.clear();
        check(m.isEmpty(), "map should be empty after clear");
        check(m.size() == 0, "size should be 0 after clear");

        // Keep k reachable until the end of the test so its entry is not collected.
        check(k.equals("k"), "key object should still be intact");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
