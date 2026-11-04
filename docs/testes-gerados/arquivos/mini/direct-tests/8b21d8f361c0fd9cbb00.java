/*
 * @test
 * @summary Test java.util.HashMap.forEach(Consumer) method behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithEmptyMap();
        testForEachWithValues();
        testForEachConcurrentModification();
    }

    static void testForEachWithNullConsumer() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachWithEmptyMap() {
        HashMap<String, String> map = new HashMap<>();
        final int[] count = {0};
        map.forEach(value -> count[0]++);
        if (count[0] != 0) {
            throw new Error("forEach on empty map should not invoke action");
        }
    }

    static void testForEachWithValues() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        map.forEach(value -> sb.append(value).append(","));

        String result = sb.toString();
        // The order is not guaranteed, but all values must be present
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new Error("forEach did not visit all values");
        }
    }

    static void testForEachConcurrentModification() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");

        try {
            map.forEach(value -> {
                if ("one".equals(value)) {
                    map.put(3, "three"); // structural modification during iteration
                }
            });
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
