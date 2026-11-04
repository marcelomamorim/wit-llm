/*
 * @test
 * @summary Test deterministic behavior of WeakHashMap.next() including ConcurrentModificationException and NoSuchElementException
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNextNormalIteration();
        testNextConcurrentModificationException();
        testNextNoSuchElementException();
    }

    private static void testNextNormalIteration() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();
        map.put(key1, "value1");
        map.put(key2, "value2");

        Iterator<Map.Entry<Object, String>> it = map.entrySet().iterator();

        // Should return two entries without exception
        Map.Entry<Object, String> e1 = it.next();
        if (e1 == null || e1.getKey() == null || e1.getValue() == null) {
            throw new AssertionError("First entry or its key/value is null");
        }
        Map.Entry<Object, String> e2 = it.next();
        if (e2 == null || e2.getKey() == null || e2.getValue() == null) {
            throw new AssertionError("Second entry or its key/value is null");
        }
    }

    private static void testNextConcurrentModificationException() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, "value");

        Iterator<Map.Entry<Object, String>> it = map.entrySet().iterator();

        // Modify map after iterator creation to trigger ConcurrentModificationException
        map.put(new Object(), "value2");

        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }

    private static void testNextNoSuchElementException() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, "value");

        Iterator<Map.Entry<Object, String>> it = map.entrySet().iterator();

        // Consume the only element
        it.next();

        // Next call should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
