/*
 * @test
 * @summary Tests for java.util.WeakHashMap.next()
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.NoSuchElementException;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsValue();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsValue() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        WeakHashMap.Entry<String, String> entry = getFirstEntry(map);
        if (entry != null) {
            WeakHashMap.ValueIterator iterator = new WeakHashMap.ValueIterator(map);
            iterator.next();
            String value = iterator.next();
            if (!"value1".equals(value)) {
                throw new AssertionError("Expected value 'value1', but got: " + value);
            }
        } else {
            throw new AssertionError("Map should have at least one entry.");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        WeakHashMap.Entry<String, String> entry = getFirstEntry(map);
        if (entry != null) {
            WeakHashMap.ValueIterator iterator = new WeakHashMap.ValueIterator(map);
            iterator.next();
            try {
                iterator.next();
                throw new AssertionError("Expected NoSuchElementException to be thrown.");
            } catch (NoSuchElementException e) {
                // Expected exception
            }
        } else {
            // Map is empty, so next() should throw immediately
            WeakHashMap.ValueIterator iterator = new WeakHashMap.ValueIterator(map);
            try {
                iterator.next();
                throw new AssertionError("Expected NoSuchElementException to be thrown.");
            } catch (NoSuchElementException e) {
                // Expected exception
            }
        }
    }

    private static <K, V> WeakHashMap.Entry<K, V> getFirstEntry(WeakHashMap<K, V> map) {
        // Accessing internal entry set via public API is not possible; simulate by inserting an entry
        // and then removing it to check if map is empty.
        // Since no public API to get entries, this method is a placeholder for test setup.
        // For the purpose of this test, assume map has at least one entry if not empty.
        return null; // No direct way; rely on map size or external setup if needed.
    }
}
