/*
 * @test
 * @summary Test java.util.WeakHashMap next() method behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.Map;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsKey();
        testNextOnEmptyThrows();
    }

    private static void testNextReturnsKey() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, "value1");
        map.put(key2, "value2");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator unexpectedly empty");
        }

        // The next() method of the iterator returns the entry, we want to test nextEntry().getKey() indirectly
        // Since nextEntry() is not public, we test next() on the iterator and getKey() on the entry
        Map.Entry<String, String> entry = it.next();
        String key = entry.getKey();

        if (!key.equals(key1) && !key.equals(key2)) {
            throw new RuntimeException("Returned key is not one of the inserted keys");
        }

        // Also test the public next() method of the iterator returns the key via nextEntry().getKey() indirectly
        // We cannot call nextEntry() directly, so this is the best observable behavior
    }

    private static void testNextOnEmptyThrows() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
