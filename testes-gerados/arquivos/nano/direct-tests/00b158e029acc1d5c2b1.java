/* @test    @summary Test LinkedHashMap.next() method for correct key retrieval.    @run main LinkedHashMapWitupTest */

import java.util.LinkedHashMap;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectKey();
        testNextOnEmptyMap();
        testNextAfterRemoval();
    }

    private static void testNextReturnsCorrectKey() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        // Iterate over the map and verify next() returns keys in insertion order
        for (String key : map.keySet()) {
            String returnedKey = map.next();
            assert returnedKey.equals(key) : "Expected key: " + key + ", but got: " + returnedKey;
        }
    }

    private static void testNextOnEmptyMap() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        try {
            map.next();
            assert false : "Expected NoSuchElementException when calling next() on empty map";
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testNextAfterRemoval() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 10);
        map.put("y", 20);
        // Remove an element and then call next()
        map.remove("x");
        String key = map.next();
        assert key.equals("y") : "Expected key: y, but got: " + key;
    }
}
