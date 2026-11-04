/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEachRemaining behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;
import java.util.Iterator;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingOnEmptyMap();
        testForEachRemainingOnPopulatedMap();
        testForEachRemainingStopsAtFence();
    }

    static void testForEachRemainingNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        try {
            // The iterator's forEachRemaining should throw NullPointerException if action is null
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachRemainingOnEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        final StringBuilder sb = new StringBuilder();
        it.forEachRemaining(e -> sb.append(e.getValue()));
        if (sb.length() != 0) {
            throw new AssertionError("Expected no elements to be processed on empty map");
        }
    }

    static void testForEachRemainingOnPopulatedMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        final StringBuilder sb = new StringBuilder();
        it.forEachRemaining(e -> sb.append(e.getValue()).append(","));
        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("forEachRemaining did not process all values: " + result);
        }
    }

    static void testForEachRemainingStopsAtFence() {
        // This test indirectly verifies that forEachRemaining stops at fence (end) key
        // by creating a subMap view and iterating over it
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");

        // Create a subMap from 2 (inclusive) to 5 (exclusive)
        ConcurrentSkipListMap<Integer, String> subMap = new ConcurrentSkipListMap<>(map.subMap(2, 5));
        Iterator<Map.Entry<Integer, String>> it = subMap.entrySet().iterator();
        final StringBuilder sb = new StringBuilder();
        it.forEachRemaining(e -> sb.append(e.getValue()).append(","));
        String result = sb.toString();

        if (!result.contains("two") || !result.contains("three") || !result.contains("four")) {
            throw new AssertionError("forEachRemaining did not process all subMap values: " + result);
        }
        if (result.contains("one") || result.contains("five")) {
            throw new AssertionError("forEachRemaining processed values outside subMap fence: " + result);
        }
    }
}
