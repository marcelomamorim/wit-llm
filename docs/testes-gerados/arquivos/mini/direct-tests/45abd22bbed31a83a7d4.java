/*
 * @test
 * @summary Test java.util.HashMap.forEach method behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachEmptyMap();
        testForEachNonEmptyMap();
        testForEachConcurrentModification();
    }

    static void testForEachNullAction() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachEmptyMap() {
        HashMap<String, String> map = new HashMap<>();
        final int[] count = {0};
        map.forEach(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new Error("forEach on empty map should not invoke action");
        }
    }

    static void testForEachNonEmptyMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append(e.getValue());
            }
        });
        String result = sb.toString();
        if (!(result.contains("a1") && result.contains("b2") && result.contains("c3"))) {
            throw new Error("forEach did not visit all entries: " + result);
        }
    }

    static void testForEachConcurrentModification() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("x", "1");
        map.put("y", "2");
        try {
            map.forEach(new Consumer<Map.Entry<String, String>>() {
                public void accept(Map.Entry<String, String> e) {
                    map.put("z", "3"); // modifies map during iteration
                }
            });
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
