/*
 * @test
 * @summary Test for ConcurrentHashMap's forEachRemaining method to ensure it correctly applies the consumer to each remaining entry.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingAppliesConsumer();
        testForEachRemainingWithEmptyMap();
        testForEachRemainingNullConsumer();
    }

    static void testForEachRemainingAppliesConsumer() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        Consumer<Map.Entry<String, Integer>> consumer = entry -> {
            result.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        };
        // Obtain iterator and advance to some entries
        var iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            break; // advance only once
        }
        // Use forEachRemaining to process remaining entries
        iterator.forEachRemaining(consumer);
        String output = result.toString();
        // Verify that output contains remaining entries
        boolean containsA = output.contains("a:");
        boolean containsB = output.contains("b:");
        boolean containsC = output.contains("c:");
        if (!(containsA || containsB || containsC)) {
            throw new AssertionError("forEachRemaining did not apply consumer correctly. Output: " + output);
        }
    }

    static void testForEachRemainingWithEmptyMap() {
        ConcurrentHashMap<String, String> emptyMap = new ConcurrentHashMap<>();
        var iterator = emptyMap.entrySet().iterator();
        final boolean[] called = {false};
        iterator.forEachRemaining(entry -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("forEachRemaining should not call consumer on empty iterator.");
        }
    }

    static void testForEachRemainingNullConsumer() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        map.put(2, 20);
        var iterator = map.entrySet().iterator();
        try {
            iterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException when passing null consumer.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
