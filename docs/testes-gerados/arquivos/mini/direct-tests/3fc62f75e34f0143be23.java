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
        testNullActionThrows();
        testForEachIteratesAllKeys();
        testConcurrentModificationThrows();
    }

    static void testNullActionThrows() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachIteratesAllKeys() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        // The keys must all be present in the output string
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new AssertionError("forEach did not iterate all keys: " + result);
        }
    }

    static void testConcurrentModificationThrows() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        try {
            map.forEach(new Consumer<String>() {
                public void accept(String s) {
                    if ("a".equals(s)) {
                        map.put("c", "C"); // structural modification during iteration
                    }
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
