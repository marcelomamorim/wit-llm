/*
 * @test
 * @summary Test LinkedHashMap.next() method from its iterator
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        // Create a LinkedHashMap with some entries
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        // Get the iterator over the values
        Iterator<String> it = map.values().iterator();

        // Test next() returns values in insertion order
        String v1 = it.next();
        if (!"1".equals(v1)) {
            throw new RuntimeException("Expected first value '1', got: " + v1);
        }

        String v2 = it.next();
        if (!"2".equals(v2)) {
            throw new RuntimeException("Expected second value '2', got: " + v2);
        }

        String v3 = it.next();
        if (!"3".equals(v3)) {
            throw new RuntimeException("Expected third value '3', got: " + v3);
        }

        // After all elements, next() should throw NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected NoSuchElementException after last element");
        }
    }
}
