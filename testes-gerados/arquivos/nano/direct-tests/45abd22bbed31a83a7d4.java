/* @test    @summary Test HashMap forEach method for correct iteration and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachWithEntries() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder sb = new StringBuilder();
        map.forEach(entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(","));
        String result = sb.toString();
        boolean hasA = result.contains("a:1");
        boolean hasB = result.contains("b:2");
        boolean hasC = result.contains("c:3");
        if (!(hasA && hasB && hasC)) {
            throw new AssertionError("forEach did not process all entries correctly: " + result);
        }
    }

    static void testForEachWithNullAction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 10);
        boolean threw = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("k1", 1);
        map.put("k2", 2);
        final boolean[] exceptionThrown = {false};
        try {
            map.forEach(entry -> {
                map.put("k3", 3);
                entry.getKey(); // just to use the entry
            });
        } catch (ConcurrentModificationException e) {
            exceptionThrown[0] = true;
        }
        if (!exceptionThrown[0]) {
            throw new AssertionError("Expected ConcurrentModificationException during forEach with modification");
        }
    }
}
