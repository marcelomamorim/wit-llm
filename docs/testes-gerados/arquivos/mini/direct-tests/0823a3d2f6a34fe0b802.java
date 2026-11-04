/*
 * @test
 * @summary Test Hashtable constructor with Map argument
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testConstructorWithEmptyMap();
        testConstructorWithNonEmptyMap();
    }

    private static void testConstructorWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        Hashtable<String, String> ht = new Hashtable<>(emptyMap);
        if (ht.size() != 0) {
            throw new RuntimeException("Expected empty Hashtable size 0, got " + ht.size());
        }
    }

    private static void testConstructorWithNonEmptyMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Hashtable<String, Integer> ht = new Hashtable<>(map);

        if (ht.size() != map.size()) {
            throw new RuntimeException("Hashtable size " + ht.size() + " does not match map size " + map.size());
        }

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            Integer val = ht.get(e.getKey());
            if (val == null || !val.equals(e.getValue())) {
                throw new RuntimeException("Hashtable value for key '" + e.getKey() + "' incorrect: expected " + e.getValue() + ", got " + val);
            }
        }
    }
}
