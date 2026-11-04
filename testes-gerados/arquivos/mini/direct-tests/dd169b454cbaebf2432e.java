/*
 * @test
 * @summary Test ConcurrentHashMap.forEach(Consumer) method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.concurrent.ConcurrentHashMap.Traverser;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachOnEmptyMap();
        testForEachOnPopulatedMap();
    }

    static void testForEachWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.forEach(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachOnEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        final int[] count = {0};
        map.forEach(new Consumer<String>() {
            public void accept(String v) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new Error("forEach on empty map should not invoke action");
        }
    }

    static void testForEachOnPopulatedMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            public void accept(String v) {
                sb.append(v).append(",");
            }
        });

        String result = sb.toString();
        // The result should contain all values separated by commas
        if (!result.contains("1") || !result.contains("2") || !result.contains("3")) {
            throw new Error("forEach did not visit all values: " + result);
        }
        // The number of commas should be 3
        int commas = 0;
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == ',') commas++;
        }
        if (commas != 3) {
            throw new Error("forEach action count mismatch, expected 3 but got " + commas);
        }
    }
}
