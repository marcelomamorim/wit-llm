/*
 * @test
 * @summary Test java.util.HashMap.KeyIterator.next() for expected exceptions and normal behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextNoSuchElementException();
        testNextConcurrentModificationException();
    }

    // Test normal iteration with next() returns keys in expected order
    private static void testNextNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        Iterator<String> it = map.keySet().iterator();

        // Collect keys to verify iteration
        int count = 0;
        while (it.hasNext()) {
            String key = it.next();
            if (key == null) {
                throw new AssertionError("Key should not be null");
            }
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Expected 3 keys, got " + count);
        }
    }

    // Test that next() throws NoSuchElementException when no more elements
    private static void testNextNoSuchElementException() {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> it = map.keySet().iterator();

        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no elements");
        }

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }

    // Test that next() throws ConcurrentModificationException if map modified after iterator creation
    private static void testNextConcurrentModificationException() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        Iterator<String> it = map.keySet().iterator();

        // Modify map after iterator creation
        map.put("anotherKey", "anotherValue");

        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException when map modified after iterator creation");
        }
    }
}
