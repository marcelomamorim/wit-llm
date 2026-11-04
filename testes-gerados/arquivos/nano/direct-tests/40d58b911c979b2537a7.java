/*
 * @test
 * @summary Test forEach method of WeakHashMap
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachNullAction();
    }

    static void testForEachWithEntries() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> {
            result.append(k).append(v);
        });
        String output = result.toString();
        boolean condition = output.contains("one1") && output.contains("two2");
        if (!condition) {
            throw new AssertionError("Expected output to contain 'one1' and 'two2', but got: " + output);
        }
    }

    static void testForEachWithEmptyMap() {
        WeakHashMap<String, String> emptyMap = new WeakHashMap<>();
        StringBuilder result = new StringBuilder();
        emptyMap.forEach((k, v) -> {
            result.append(k).append(v);
        });
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result for empty map, but got: " + result.toString());
        }
    }

    static void testForEachNullAction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
