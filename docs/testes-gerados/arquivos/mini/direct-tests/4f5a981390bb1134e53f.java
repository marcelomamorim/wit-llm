/*
 * @test
 * @summary Test java.util.HashMap remove() method behavior
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNext();
        testRemoveAfterConcurrentModification();
        testRemoveSuccessful();
    }

    static void testRemoveWithoutNext() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        Iterator<String> it = map.keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    static void testRemoveAfterConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        it.next();
        map.put("anotherKey", "anotherValue"); // structural modification
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException cme) {
            // expected
        }
    }

    static void testRemoveSuccessful() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        String key = it.next();
        it.remove();
        if (map.containsKey(key)) {
            throw new RuntimeException("Key should have been removed from map");
        }
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }
}
