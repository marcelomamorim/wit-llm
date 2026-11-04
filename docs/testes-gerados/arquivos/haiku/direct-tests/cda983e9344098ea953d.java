/*
 * @test
 * @summary Tests the public HashMap(int initialCapacity) constructor
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testZeroCapacityIsUsable();
        testSmallCapacityGrowsAndKeepsMappings();
        testLargeCapacityConstructedEmpty();
        testNegativeCapacityRejected();
    }

    static void testZeroCapacityIsUsable() {
        HashMap<String, Integer> map = new HashMap<>(0);
        check(map.isEmpty(), "new HashMap(0) should be empty");
        check(map.size() == 0, "new HashMap(0) size should be 0");
        check(map.get("missing") == null, "get on empty map should return null");
        map.put("a", 1);
        check(map.size() == 1, "size after one put should be 1");
        check(map.get("a") == 1, "value for key a should be 1");
    }

    static void testSmallCapacityGrowsAndKeepsMappings() {
        HashMap<Integer, String> map = new HashMap<>(1);
        for (int i = 0; i < 1000; i++) {
            map.put(i, "v" + i);
        }
        check(map.size() == 1000, "size should be 1000 after 1000 distinct puts");
        for (int i = 0; i < 1000; i++) {
            check(("v" + i).equals(map.get(i)), "mapping lost after resize for key " + i);
        }
        check(map.containsKey(0), "key 0 should be present");
        check(!map.containsKey(1000), "key 1000 should be absent");
    }

    static void testLargeCapacityConstructedEmpty() {
        HashMap<String, String> map = new HashMap<>(1 << 20);
        check(map.isEmpty(), "map with large initial capacity should be empty");
        check(map.size() == 0, "size should be 0");
    }

    static void testNegativeCapacityRejected() {
        boolean thrown = false;
        try {
            new HashMap<String, String>(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative initial capacity should throw IllegalArgumentException");
    }
}
