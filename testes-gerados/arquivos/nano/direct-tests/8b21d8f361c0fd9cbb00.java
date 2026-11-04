/* @test    @summary Test HashMap forEach method for correct iteration and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachWithElements() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        map.forEach(value -> result.append(value));
        int sum = 0;
        for (int v : map.values()) {
            sum += v;
        }
        if (result.toString().length() != map.size()) {
            throw new AssertionError("forEach did not process all elements") ;
        }
        if (sum != 6) {
            throw new AssertionError("Sum of values mismatch") ;
        }
    }

    static void testForEachWithNullAction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 10);
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null action") ;
        }
    }

    static void testForEachConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("k1", 100);
        map.put("k2", 200);
        final boolean[] exceptionThrown = {false};
        try {
            map.forEach(value -> {
                map.put("new", 999);
            });
        } catch (ConcurrentModificationException e) {
            exceptionThrown[0] = true;
        }
        if (!exceptionThrown[0]) {
            throw new AssertionError("Expected ConcurrentModificationException during forEach") ;
        }
    }
}
