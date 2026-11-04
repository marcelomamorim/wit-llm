/*
 * @test
 * @summary Test WeakHashMap.ValueIterator.next() for ConcurrentModificationException and NoSuchElementException
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNextThrowsConcurrentModificationException();
        testNextThrowsNoSuchElementException();
        testNextReturnsValue();
    }

    private static void testNextThrowsConcurrentModificationException() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, "value");
        Iterator<String> it = map.values().iterator();
        // Modify map after iterator creation to cause ConcurrentModificationException
        map.put(new Object(), "another");
        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Iterator<String> it = map.values().iterator();
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected
        }
    }

    private static void testNextReturnsValue() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        String value = "testValue";
        map.put(key, value);
        Iterator<String> it = map.values().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String returned = it.next();
        if (!value.equals(returned)) {
            throw new AssertionError("Returned value does not match expected value");
        }
    }
}
