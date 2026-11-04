/*
 * @test
 * @summary Test java.util.WeakHashMap remove() method behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.lang.IllegalStateException;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesEntry();
        testRemoveConcurrentModificationThrows();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNextThrows() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        Iterator<String> it = map.keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that remove() after next() removes the current key from the map
    private static void testRemoveAfterNextRemovesEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<String> it = map.keySet().iterator();
        String firstKey = it.next();
        it.remove();

        if (map.containsKey(firstKey)) {
            throw new RuntimeException("Remove did not remove the key from the map");
        }

        // The iterator should still work for remaining keys
        if (!it.hasNext() && map.size() != 1) {
            throw new RuntimeException("Iterator state inconsistent after remove");
        }
    }

    // Test that remove() throws ConcurrentModificationException if map modified externally
    private static void testRemoveConcurrentModificationThrows() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<String> it = map.keySet().iterator();
        it.next();

        // Modify map externally
        map.put("key3", "value3");

        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
