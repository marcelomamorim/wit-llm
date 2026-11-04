/*
 * @test
 * @summary Test java.util.HashMap.forEach method behavior and exception
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachBasicIteration();
        testForEachConcurrentModification();
    }

    private static void testForEachNullAction() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachBasicIteration() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEach did not iterate all entries: " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        final Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            private boolean first = true;
            public void accept(Map.Entry<String, String> e) {
                if (first) {
                    first = false;
                    map.put("c", "3"); // Structural modification during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }
}
