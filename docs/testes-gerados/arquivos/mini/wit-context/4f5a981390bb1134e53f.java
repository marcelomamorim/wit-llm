/*
 * @test
 * @summary Test HashMap.HashIterator.remove() for expected exceptions and normal behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNext();
        testRemoveAfterConcurrentModification();
        testRemoveNormal();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNext() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        Iterator<String> it = map.keySet().iterator();
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling remove() before next()");
        }
    }

    // Test that remove() throws ConcurrentModificationException if map modified concurrently
    private static void testRemoveAfterConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        it.next();
        // Modify map concurrently
        map.put("key3", "value3");
        boolean threw = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected ConcurrentModificationException when calling remove() after concurrent modification");
        }
    }

    // Test normal remove() after next() without concurrent modification
    private static void testRemoveNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        String key = it.next();
        it.remove();
        if (map.containsKey(key)) {
            throw new RuntimeException("Key " + key + " should have been removed from map");
        }
    }
}
