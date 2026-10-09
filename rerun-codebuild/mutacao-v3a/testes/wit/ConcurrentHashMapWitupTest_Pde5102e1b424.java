/*
 * @test
 * @summary Tests for ConcurrentHashMap forEach method behavior.
 * @run main ConcurrentHashMapWitupTest_Pde5102e1b424
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pde5102e1b424 {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithEmptyMap();
        testForEachWithPopulatedMap();
    }

    static void testForEachWithNullAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        final boolean[] called = {false};
        map.forEach((k, v) -> {
            called[0] = true;
            throw new AssertionError("This should not be called for empty map");
        });
        if (called[0]) {
            throw new AssertionError("Action should not be called for empty map");
        }
    }

    static void testForEachWithPopulatedMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb.append(k).append(v));
        String result = sb.toString();
        if (!(result.contains("a1") && result.contains("b2"))) {
            throw new AssertionError("Expected to find entries in result: " + result);
        }
    }
}
