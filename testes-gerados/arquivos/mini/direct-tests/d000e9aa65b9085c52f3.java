/*
 * @test
 * @summary Test LinkedHashMap.forEach(Consumer) for normal iteration, null action, and concurrent modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachNormal() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
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
        if (!result.equals("one,two,three,")) {
            throw new AssertionError("Expected iteration order 'one,two,three,' but got: " + result);
        }
    }

    static void testForEachNullAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        final Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                if ("a".equals(s)) {
                    map.put("c", 3); // structural modification during iteration
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
