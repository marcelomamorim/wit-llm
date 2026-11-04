/*
 * @test
 * @summary Test for ConcurrentSkipListMap.clear() method.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testClearWithNullKey();
        testClearWithEmptyMap();
        testClearWithMultipleEntries();
    }

    private static void testClearWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "valueA");
        map.put("b", "valueB");
        map.put(null, "nullValue"); // Should throw NullPointerException
        try {
            map.clear();
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown in testClearWithNullKey");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testClearWithEmptyMap() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.clear(); // Should not throw
        if (!map.isEmpty()) {
            System.err.println("Map not empty after clear in testClearWithEmptyMap");
        }
    }

    private static void testClearWithMultipleEntries() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("x", "valueX");
        map.put("y", "valueY");
        map.put("z", "valueZ");
        map.clear();
        if (!map.isEmpty()) {
            System.err.println("Map not empty after clear in testClearWithMultipleEntries");
        }
    }
}
