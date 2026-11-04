/*
 * @test
 * @summary Test IdentityHashMap.EntryIterator.Entry.getKey() behavior including IllegalStateException when index < 0
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testGetKeyNormal();
        testGetKeyIllegalStateException();
    }

    static void testGetKeyNormal() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key = "key";
        String value = "value";
        map.put(key, value);

        // Use entrySet iterator to get Entry and call getKey()
        java.util.Iterator<java.util.Map.Entry<String, String>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator unexpectedly empty");
        }
        java.util.Map.Entry<String, String> entry = it.next();
        String returnedKey = entry.getKey();

        if (returnedKey != key) {
            throw new RuntimeException("getKey() did not return the expected key instance");
        }
    }

    static void testGetKeyIllegalStateException() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();

        // Create iterator and call next() to move index, then remove to invalidate index
        java.util.Iterator<java.util.Map.Entry<String, String>> it = map.entrySet().iterator();

        // The iterator is empty, so next() will throw NoSuchElementException
        // Instead, we test the behavior of getKey() on Entry with invalid index by
        // creating an EntryIterator.Entry via reflection is not possible (private class)
        // So we test the public contract: calling getKey() on an Entry after iterator
        // remove or before next() is called throws IllegalStateException

        // The Map.Entry returned by iterator is not accessible before next(), so we test
        // that calling getKey() on Entry after remove throws IllegalStateException

        // To do this, we add one entry, get iterator, call next(), call remove(), then
        // try to call getKey() on the removed Entry.

        String key = "key";
        String value = "value";
        map.put(key, value);

        it = map.entrySet().iterator();
        java.util.Map.Entry<String, String> entry = it.next();
        it.remove();

        boolean threw = false;
        try {
            entry.getKey();
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling getKey() on removed Entry");
        }
    }
}
