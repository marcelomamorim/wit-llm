/*
 * @test
 * @summary Test ConcurrentSkipListMap remove() method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Iterator;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithLastReturned();
    }

    // This test verifies that calling remove() without a lastReturned node throws IllegalStateException
    private static void testRemoveWithoutLastReturned() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        // We do not call next(), so lastReturned is null
        try {
            // We simulate the remove() method from the excerpt, which throws if lastReturned == null
            // Since lastReturned is private, we simulate by calling Iterator.remove() without next()
            it.remove();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    // This test verifies that remove() removes the last returned key from the map
    private static void testRemoveWithLastReturned() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        // Advance iterator to first element
        Map.Entry<Integer, String> entry = it.next();
        if (!entry.getKey().equals(1)) {
            throw new AssertionError("Unexpected first key: " + entry.getKey());
        }

        // Remove the last returned element (key=1)
        it.remove();

        // Verify that key=1 is removed
        if (map.containsKey(1)) {
            throw new AssertionError("Key 1 should have been removed");
        }

        // Verify other keys remain
        if (!map.containsKey(2) || !map.containsKey(3)) {
            throw new AssertionError("Keys 2 and 3 should remain");
        }
    }
}
