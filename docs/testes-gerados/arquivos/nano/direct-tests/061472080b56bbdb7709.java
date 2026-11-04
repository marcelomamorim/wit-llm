/*
 * @test
 * @summary Test the forEach method of LinkedHashMap for correct iteration and exception handling
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.Map;

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
        Consumer<Map.Entry<String, Integer>> consumer = entry -> sb.append(entry.getKey());
        map.forEach(consumer);
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
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("k1", 100);
        map.put("k2", 200);
        Consumer<Map.Entry<String, Integer>> consumer = new Consumer<Map.Entry<String, Integer>>() {
            boolean first = true;
            @Override
            public void accept(Map.Entry<String, Integer> entry) {
                if (first) {
                    map.put("k3", 300); // modify during iteration
                    first = false;
                }
            }
        };
        try {
            map.forEach(consumer);
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
