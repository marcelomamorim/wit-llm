/*
 * @test
 * @summary Test java.util.HashMap.forEach(Consumer<? super V>) for NPE and normal behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
        testForEachConcurrentModification();
    }

    private static void testForEachNullAction() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String v) {
                sb.append(v).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!(result.contains("value1") && result.contains("value2"))) {
            throw new Error("forEach did not process all values: " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        final Consumer<String> action = new Consumer<String>() {
            private boolean first = true;
            public void accept(String v) {
                if (first) {
                    first = false;
                    map.put("c", "C"); // structural modification during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new Error("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
