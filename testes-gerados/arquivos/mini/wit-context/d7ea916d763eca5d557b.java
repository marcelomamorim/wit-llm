/*
 * @test
 * @summary Test ConcurrentSkipListMap.SubMap.SubMapIter.remove() for IllegalStateException when lastReturned is null
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.SortedMap;
import java.util.NavigableMap;
import java.util.Map;
import java.util.Iterator;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithLastReturned();
    }

    // This test tries to invoke remove() on SubMapIter when lastReturned == null, expecting IllegalStateException
    private static void testRemoveWithoutLastReturned() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        // Create a subMap to get SubMapIter
        NavigableMap<Integer, String> subMap = map.subMap(1, true, 2, true);
        Iterator<Map.Entry<Integer, String>> it = subMap.entrySet().iterator();

        // The iterator is a SubMapIter, but lastReturned is initially null
        // Calling remove() immediately should throw IllegalStateException
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when remove() called before next()");
        }
    }

    // This test calls next() then remove() to verify normal remove behavior
    private static void testRemoveWithLastReturned() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        NavigableMap<Integer, String> subMap = map.subMap(1, true, 2, true);
        Iterator<Map.Entry<Integer, String>> it = subMap.entrySet().iterator();

        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }

        Map.Entry<Integer, String> entry = it.next();
        if (entry.getKey() != 1 || !"one".equals(entry.getValue())) {
            throw new AssertionError("Unexpected first entry");
        }

        it.remove(); // should succeed without exception

        if (map.containsKey(1)) {
            throw new AssertionError("Entry with key 1 should have been removed");
        }

        // Calling remove() again without next() should throw IllegalStateException
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when remove() called twice without next()");
        }
    }
}
