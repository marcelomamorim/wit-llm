/*
 * @test    @summary Test forEach method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest {

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
        BiConsumer<String, Integer> consumer = (k, v) -> result.append(k).append(v);
        map.forEach(consumer);
        String output = result.toString();
        boolean condition = output.contains("a1") && output.contains("b2") && output.contains("c3");
        if (!condition) {
            throw new AssertionError("forEach did not process all entries correctly: " + output);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        StringBuilder result = new StringBuilder();
        BiConsumer<String, Integer> consumer = (k, v) -> result.append(k).append(v);
        map.forEach(consumer);
        if (result.length() != 0) {
            throw new AssertionError("forEach on empty map should not process any entries: " + result.toString());
        }
    }

    static void testForEachNullAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
