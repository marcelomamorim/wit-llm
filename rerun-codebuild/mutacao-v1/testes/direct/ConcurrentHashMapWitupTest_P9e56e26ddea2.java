/*
 * @test    @summary Test forEach method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest_P9e56e26ddea2
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_P9e56e26ddea2 {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachNullAction();
    }

    static void testForEachWithEntries() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        BiConsumer<String, Integer> consumer = (k, v) -> {
            result.append(k).append(v);
        };
        map.forEach(consumer);
        String combined = result.toString();
        if (!(combined.contains("a1") && combined.contains("b2") && combined.contains("c3"))) {
            throw new AssertionError("Failed: forEach did not process all entries correctly. Result: " + combined);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentHashMap<String, String> emptyMap = new ConcurrentHashMap<>();
        StringBuilder result = new StringBuilder();
        BiConsumer<String, String> consumer = (k, v) -> {
            result.append(k).append(v);
        };
        emptyMap.forEach(consumer);
        if (result.length() != 0) {
            throw new AssertionError("Failed: forEach on empty map should not process any entries. Result length: " + result.length());
        }
    }

    static void testForEachNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "y");
        try {
            map.forEach(null);
            throw new AssertionError("Failed: forEach with null action should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
