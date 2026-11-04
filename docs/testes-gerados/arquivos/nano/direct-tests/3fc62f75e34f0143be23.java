/* @test    @summary Test HashMap forEach method with a simple consumer    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
        testForEachWithConcurrentModification();
    }

    static void testForEachWithValidConsumer() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = key -> sb.append(key);
        map.forEach(consumer);
        String result = sb.toString();
        boolean containsA = result.contains("a");
        boolean containsB = result.contains("b");
        boolean containsC = result.contains("c");
        if (!(containsA && containsB && containsC)) {
            throw new AssertionError("forEach did not process all keys: " + result);
        }
    }

    static void testForEachWithNullConsumer() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 10);
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachWithConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("k1", 1);
        map.put("k2", 2);
        final boolean[] exceptionThrown = {false};
        try {
            map.forEach(key -> {
                map.put("new", 99);
            });
        } catch (ConcurrentModificationException e) {
            exceptionThrown[0] = true;
        }
        if (!exceptionThrown[0]) {
            throw new AssertionError("Expected ConcurrentModificationException not thrown during forEach");
        }
    }
}
