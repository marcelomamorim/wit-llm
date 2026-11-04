/*
 * @test
 * @summary Test IdentityHashMap next() method behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        // Create IdentityHashMap and add entries
        IdentityHashMap<Object, String> map = new IdentityHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();
        map.put(key1, "value1");
        map.put(key2, "value2");

        // Obtain iterator over values
        Iterator<String> it = map.values().iterator();

        // Check that next() returns one of the inserted values
        String first = it.next();
        if (!"value1".equals(first) && !"value2".equals(first)) {
            throw new RuntimeException("Unexpected value from next(): " + first);
        }

        // Check that next() returns the other value on second call
        String second = it.next();
        if (!"value1".equals(second) && !"value2".equals(second)) {
            throw new RuntimeException("Unexpected value from next(): " + second);
        }

        if (first.equals(second)) {
            throw new RuntimeException("next() returned the same value twice");
        }

        // Check that no more elements remain
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have no more elements");
        }

        // Test that calling next() when no elements remain throws exception
        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected NoSuchElementException when calling next() with no elements");
        }
    }
}
