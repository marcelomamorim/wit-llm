/*
 * @test
 * @summary Test IdentityHashMap.next() method for expected behavior and ConcurrentModificationException
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsCorrectKey();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextReturnsCorrectKey() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, "value1");
        map.put(key2, "value2");

        Iterator<String> it = map.keySet().iterator();
        // The iterator should return keys that are in the map
        boolean foundKey1 = false;
        boolean foundKey2 = false;
        while (it.hasNext()) {
            String k = it.next();
            if (k == key1) {
                foundKey1 = true;
            } else if (k == key2) {
                foundKey2 = true;
            } else {
                throw new RuntimeException("Unexpected key returned by iterator: " + k);
            }
        }
        if (!foundKey1 || !foundKey2) {
            throw new RuntimeException("Iterator did not return all keys");
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key = new String("key");
        map.put(key, "value");

        Iterator<String> it = map.keySet().iterator();
        // Modify map after iterator creation to cause ConcurrentModificationException
        map.put(new String("anotherKey"), "anotherValue");

        try {
            it.next();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }
}
