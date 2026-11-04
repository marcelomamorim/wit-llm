/*
 * @test
 * @summary Test LinkedHashMap.forEach method for normal and exceptional behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    private static void testForEachNormal() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!result.contains("one=1") || !result.contains("two=2") || !result.contains("three=3")) {
            throw new AssertionError("forEach did not visit all entries: " + result);
        }
    }

    private static void testForEachNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key", "value");

        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        final Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                if ("a".equals(e.getKey())) {
                    map.put("c", "C"); // structural modification during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
