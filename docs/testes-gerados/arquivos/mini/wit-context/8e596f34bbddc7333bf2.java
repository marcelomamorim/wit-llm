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

    static void testForEachNullAction() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        try {
            map.forEach(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");

        final Consumer<String> action = new Consumer<String>() {
            boolean first = true;
            public void accept(String v) {
                if (first) {
                    first = false;
                    map.put(3, "three"); // structural modification during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new Error("Expected ConcurrentModificationException due to structural modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testForEachNormalIteration() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            public void accept(String v) {
                sb.append(v).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new Error("Unexpected iteration order or values: " + result);
        }
    }
}
