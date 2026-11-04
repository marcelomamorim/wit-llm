/*
 * @test
 * @summary Test java.util.WeakHashMap.KeyIterator.next() for expected exceptions and normal behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsKey();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
        System.out.println("All tests passed.");
    }

    private static void testNextReturnsKey() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String key = new String("key");
        String value = "value";
        map.put(key, value);

        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String returnedKey = it.next();
        if (!returnedKey.equals(key)) {
            throw new AssertionError("Returned key does not match inserted key");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Iterator<String> it = map.keySet().iterator();
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no next element");
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, "value1");

        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        it.next(); // consume first element

        // Modify map structurally after iterator creation to trigger CME
        map.put(key2, "value2");

        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }
}
