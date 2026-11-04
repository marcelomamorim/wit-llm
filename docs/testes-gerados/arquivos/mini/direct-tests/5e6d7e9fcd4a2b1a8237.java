/*
 * @test
 * @summary Test LinkedHashMap.forEach method behavior including null action and concurrent modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNormalIteration();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    private static void testForEachNormalIteration() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
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
        if (!result.equals("one=1,two=2,three=3,")) {
            throw new AssertionError("Unexpected iteration order or content: " + result);
        }
    }

    private static void testForEachNullAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                if ("a".equals(k)) {
                    map.put("c", 3); // modifies map during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
