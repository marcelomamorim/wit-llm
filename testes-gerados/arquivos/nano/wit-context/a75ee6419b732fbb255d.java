/*
 * @test
 * @summary Test for lowerEntry method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testLowerEntryWithNullKey();
        testLowerEntryWithExistingKey();
        testLowerEntryWithNonExistingKey();
    }

    static void testLowerEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            Map.Entry<Integer, String> result = map.lowerEntry(null);
            if (result != null) {
                throw new AssertionError("Expected null for null key, but got: " + result);
            }
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testLowerEntryWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Map.Entry<Integer, String> result = map.lowerEntry(2);
        if (result == null || result.getKey() != 1 || !"one".equals(result.getValue())) {
            throw new AssertionError("Unexpected lowerEntry for key 2: " + result);
        }
    }

    static void testLowerEntryWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Map.Entry<Integer, String> result = map.lowerEntry(2);
        if (result == null || result.getKey() != 1 || !"one".equals(result.getValue())) {
            throw new AssertionError("Unexpected lowerEntry for key 2: " + result);
        }
    }
}
