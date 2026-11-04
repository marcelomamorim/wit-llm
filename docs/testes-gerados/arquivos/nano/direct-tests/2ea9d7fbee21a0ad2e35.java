/*
 * @test
 * @summary Test forEach method of ConcurrentHashMap to ensure it correctly applies the action to each key.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachAppliesActionToAllKeys();
        testForEachWithNullActionThrows();
    }

    static void testForEachAppliesActionToAllKeys() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        Consumer<String> action = key -> result.append(key);
        map.forEach(action);
        String keysConcatenated = result.toString();
        boolean containsA = keysConcatenated.contains("a");
        boolean containsB = keysConcatenated.contains("b");
        boolean containsC = keysConcatenated.contains("c");
        if (!(containsA && containsB && containsC)) {
            throw new AssertionError("forEach did not apply action to all keys: " + keysConcatenated);
        }
    }

    static void testForEachWithNullActionThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "y");
        boolean threw = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }
}
