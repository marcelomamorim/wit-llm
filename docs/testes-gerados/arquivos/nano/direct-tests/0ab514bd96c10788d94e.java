/*
 * @test
 * @summary Test the next() method of ConcurrentSkipListMap for correct key retrieval and state progression.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectKey();
        testNextAdvancesState();
    }

    private static void testNextReturnsCorrectKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        // Initialize iterator at start
        ConcurrentSkipListMap<Integer, String>.EntryIterator iterator = map.new EntryIterator();
        // Move to first element
        Integer key = iterator.next();
        // The first key should be the smallest key in the map
        if (key == null || key != 1) {
            throw new AssertionError("Expected first key to be 1, but got: " + key);
        }
    }

    private static void testNextAdvancesState() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        // Initialize iterator
        ConcurrentSkipListMap<Integer, String>.EntryIterator iterator = map.new EntryIterator();
        Integer firstKey = iterator.next();
        if (firstKey == null || firstKey != 10) {
            throw new AssertionError("Expected first key to be 10, but got: " + firstKey);
        }
        // Next call should advance to next key
        Integer secondKey = iterator.next();
        if (secondKey == null || secondKey != 20) {
            throw new AssertionError("Expected second key to be 20, but got: " + secondKey);
        }
    }

    // Inner class to simulate iterator behavior for testing
    private static class EntryIterator {
        private final java.util.Iterator<java.util.Map.Entry<Integer, String>> iterator;
        private java.util.Map.Entry<Integer, String> current;

        EntryIterator(ConcurrentSkipListMap<Integer, String> map) {
            this.iterator = map.entrySet().iterator();
        }

        Integer next() {
            if (iterator.hasNext()) {
                current = iterator.next();
                return current.getKey();
            } else {
                return null;
            }
        }
    }
}
