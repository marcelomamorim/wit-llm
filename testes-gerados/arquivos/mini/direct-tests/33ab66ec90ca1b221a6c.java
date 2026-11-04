/*
 * @test
 * @summary Test java.util.HashMap.forEach method behavior including null action and concurrent modification
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachWithValidAction();
        testForEachWithNullAction();
        testForEachConcurrentModification();
    }

    private static void testForEachWithValidAction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        // The order is not guaranteed, but all entries must be present
        if (!result.contains("one=1") || !result.contains("two=2") || !result.contains("three=3")) {
            throw new AssertionError("forEach did not process all entries: " + result);
        }
    }

    private static void testForEachWithNullAction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                if ("a".equals(k)) {
                    map.put("c", "C"); // structural modification during iteration
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
}
