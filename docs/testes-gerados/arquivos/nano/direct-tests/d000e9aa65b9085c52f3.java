/*
 * @test
 * @summary Test LinkedHashMap forEach method for correct iteration and exception handling
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachNormal() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder sb = new StringBuilder();
        map.forEach(key -> sb.append(key));
        String result = sb.toString();
        boolean condition = result.equals("abc") || result.equals("acb") || result.equals("bac") || result.equals("bca") || result.equals("cab") || result.equals("cba");
        if (!condition) {
            throw new AssertionError("Unexpected iteration order: " + result);
        }
    }

    static void testForEachNullAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 10);
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("k1", 1);
        map.put("k2", 2);
        Consumer<String> consumer = key -> {
            if (key.equals("k1")) {
                map.put("k3", 3);
            }
        };
        try {
            map.forEach(consumer);
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
