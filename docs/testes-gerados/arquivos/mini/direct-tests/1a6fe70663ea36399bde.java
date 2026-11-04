/*
 * @test
 * @summary Test java.util.HashMap next() method behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsKeysInIteration();
    }

    private static void testNextReturnsKeysInIteration() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Iterator<String> it = map.keySet().iterator();

        // Collect keys returned by next()
        java.util.Set<String> keys = new java.util.HashSet<>();
        while (it.hasNext()) {
            String key = it.next();
            if (key == null) {
                throw new AssertionError("Key should not be null");
            }
            keys.add(key);
        }

        // Check that all inserted keys are returned
        if (!keys.contains("one") || !keys.contains("two") || !keys.contains("three")) {
            throw new AssertionError("Iterator.next() did not return all keys");
        }

        // Check that no extra keys are returned
        if (keys.size() != 3) {
            throw new AssertionError("Iterator.next() returned unexpected keys");
        }
    }
}
