/*
 * @test
 * @summary Test LinkedHashMap.forEach(Consumer) for null action and concurrent modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModification();
        testForEachNormalIteration();
    }

    private static void testForEachNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final Consumer<String> action = new Consumer<String>() {
            private boolean first = true;
            @Override
            public void accept(String key) {
                if (first) {
                    first = false;
                    map.put("key3", "value3"); // structural modification during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testForEachNormalIteration() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            @Override
            public void accept(String key) {
                sb.append(key).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new AssertionError("Unexpected iteration order or keys: " + result);
        }
    }
}
