/*
 * @test
 * @summary Test IdentityHashMap.next() method for correct behavior and ConcurrentModificationException
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsCorrectValue();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextReturnsCorrectValue() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object key1 = new Object();
        Object value1 = new Object();
        map.put(key1, value1);

        Iterator<Object> it = map.values().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        Object val = it.next();
        if (val != value1) {
            throw new AssertionError("Iterator.next() returned wrong value");
        }
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more elements");
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object key1 = new Object();
        Object value1 = new Object();
        map.put(key1, value1);

        Iterator<Object> it = map.values().iterator();

        // Modify map after iterator creation to cause ConcurrentModificationException
        map.put(new Object(), new Object());

        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }
}
