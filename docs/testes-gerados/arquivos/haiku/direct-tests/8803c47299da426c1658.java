/*
 * @test
 * @summary Verifies ConcurrentHashMap entry-set remove(Object) semantics for entries, mismatched values, null components and absent keys
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        check(!map.entrySet().remove("a"), "non-Map.Entry argument must return false");
        check(!map.entrySet().remove(null), "null argument must return false");
        check(map.size() == 2, "size must be unchanged after non-entry removals");

        check(!map.entrySet().remove(new AbstractMap.SimpleEntry<>("a", 2)),
              "entry with mismatched value must return false");
        check(map.get("a") == 1, "mapping must remain after mismatched value removal");

        check(!map.entrySet().remove(new AbstractMap.SimpleEntry<>(null, 1)),
              "entry with null key must return false");
        check(!map.entrySet().remove(new AbstractMap.SimpleEntry<>("a", null)),
              "entry with null value must return false");
        check(map.size() == 2, "size must be unchanged after null-component removals");

        check(!map.entrySet().remove(new AbstractMap.SimpleEntry<>("zz", 1)),
              "entry with absent key must return false");
        check(map.size() == 2, "size must be unchanged after absent-key removal");

        check(map.entrySet().remove(new AbstractMap.SimpleEntry<>("a", 1)),
              "matching entry must be removed and return true");
        check(!map.containsKey("a"), "key must be absent after successful removal");
        check(map.size() == 1, "size must decrease after successful removal");
        check(map.get("b") == 2, "other mappings must be preserved");

        check(!map.entrySet().remove(new AbstractMap.SimpleEntry<>("a", 1)),
              "removing an already-removed entry must return false");

        Map.Entry<String, Integer> live = null;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            live = e;
        }
        check(live != null, "entry set should expose remaining entry");
        check(map.entrySet().remove(live), "live entry from iteration must be removable");
        check(map.isEmpty(), "map must be empty after removing its only entry");
    }
}
