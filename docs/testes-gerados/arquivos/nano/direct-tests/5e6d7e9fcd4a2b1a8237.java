/* @test    @summary Test LinkedHashMap forEach method for correct iteration and exception handling    @run main LinkedHashMapWitupTest */

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testForEachIterationOrder();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachIterationOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> result.append(k).append(v));
        String expected1 = "a1b2c3";
        String expected2 = "a1b2c3";
        if (!result.toString().equals(expected1) && !result.toString().equals(expected2)) {
            throw new AssertionError("Iteration order mismatch: " + result);
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
        final boolean[] exceptionThrown = {false};
        try {
            map.forEach((k, v) -> {
                if (k.equals("k1")) {
                    map.put("k3", 300); // modify during iteration
                }
            });
        } catch (ConcurrentModificationException e) {
            exceptionThrown[0] = true;
        }
        if (!exceptionThrown[0]) {
            throw new AssertionError("ConcurrentModificationException not thrown during modification");
        }
    }
}
