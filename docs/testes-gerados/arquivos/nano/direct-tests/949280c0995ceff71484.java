/*
 * @test
 * @summary Test forEach method of ConcurrentHashMap to ensure it correctly applies the action to each entry.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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
        map.forEach(entry -> {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        });
        String output = result.toString();
        boolean condition = output.contains("a=1") && output.contains("b=2") && output.contains("c=3");
        if (!condition) {
            throw new AssertionError("forEach did not process all entries correctly: " + output);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentHashMap<String, String> emptyMap = new ConcurrentHashMap<>();
        final boolean[] called = {false};
        emptyMap.forEach(entry -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("forEach should not call action on empty map");
        }
    }

    static void testForEachNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "y");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
