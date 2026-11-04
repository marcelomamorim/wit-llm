/*
 * @test
 * @summary Test LinkedHashMap.forEach with normal and exceptional cases
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNormalIteration();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachNormalIteration() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<Map.Entry<String, Integer>>() {
            public void accept(Map.Entry<String, Integer> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("one=1,two=2,three=3,")) {
            throw new AssertionError("Unexpected iteration order or content: " + result);
        }
    }

    static void testForEachNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        try {
            map.forEach(new Consumer<Map.Entry<String, String>>() {
                public void accept(Map.Entry<String, String> e) {
                    if ("a".equals(e.getKey())) {
                        map.put("c", "C"); // structural modification during iteration
                    }
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
