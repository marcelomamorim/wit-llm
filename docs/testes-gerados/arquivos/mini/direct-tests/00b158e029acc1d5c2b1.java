/*
 * @test
 * @summary Test LinkedHashMap iterator next() method returns keys in insertion order
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Iterator;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Iterator<String> it = map.keySet().iterator();

        // Test that next() returns keys in insertion order
        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected first key 'one', got: " + first);
        }

        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected second key 'two', got: " + second);
        }

        String third = it.next();
        if (!"three".equals(third)) {
            throw new AssertionError("Expected third key 'three', got: " + third);
        }

        // Test that calling next() when no more elements throws NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when calling next() with no elements");
        }
    }
}
