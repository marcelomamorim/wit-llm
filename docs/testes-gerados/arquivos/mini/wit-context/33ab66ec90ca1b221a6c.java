/*
 * @test
 * @summary Test java.util.HashMap.forEach method behavior and exception
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachBasicIteration();
        testForEachConcurrentModification();
        System.out.println("All tests passed.");
    }

    private static void testForEachNullAction() {
        HashMap<String, String> map = new HashMap<>();
        boolean npeThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachBasicIteration() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEach did not iterate all entries correctly: " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            private boolean first = true;
            public void accept(String k, String v) {
                if (first) {
                    first = false;
                    map.put("c", "3"); // structural modification during iteration
                }
            }
        };

        boolean cmeThrown = false;
        try {
            map.forEach(action);
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException on concurrent modification");
        }
    }
}
