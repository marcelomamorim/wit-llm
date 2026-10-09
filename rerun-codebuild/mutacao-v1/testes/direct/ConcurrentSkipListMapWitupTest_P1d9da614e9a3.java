/*
 * @test
 * @summary Test forEach method of ConcurrentSkipListMap to ensure it correctly applies the action to all entries.
 * @run main ConcurrentSkipListMapWitupTest_P1d9da614e9a3
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_P1d9da614e9a3 {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachWithNullAction();
    }

    static void testForEachWithEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder result = new StringBuilder();
        BiConsumer<Integer, String> action = (k, v) -> result.append(k).append(":").append(v).append(",");
        map.forEach(action);
        String output = result.toString();
        if (!output.contains("1:one") || !output.contains("2:two") || !output.contains("3:three")) {
            throw new AssertionError("forEach did not process all entries: " + output);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        StringBuilder result = new StringBuilder();
        BiConsumer<Integer, String> action = (k, v) -> result.append(k).append(":").append(v);
        map.forEach(action); // should not throw or process anything
        if (result.length() != 0) {
            throw new AssertionError("forEach on empty map should not process entries: " + result);
        }
    }

    static void testForEachWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
