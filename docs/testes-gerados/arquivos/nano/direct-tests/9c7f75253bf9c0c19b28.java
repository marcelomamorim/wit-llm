/*
 * @test
 * @summary Test clear() method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testClearRemovesAllEntries();
        testClearOnEmptyMap();
    }

    private static void testClearRemovesAllEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        assert map.size() == 3 : "Map should have 3 entries before clear";
        map.clear();
        assert map.isEmpty() : "Map should be empty after clear";
        assert map.get(1) == null : "Entry with key 1 should be removed";
        assert map.get(2) == null : "Entry with key 2 should be removed";
        assert map.get(3) == null : "Entry with key 3 should be removed";
    }

    private static void testClearOnEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        assert map.isEmpty() : "Map should be initially empty";
        map.clear();
        assert map.isEmpty() : "Map should remain empty after clear";
    }
}
