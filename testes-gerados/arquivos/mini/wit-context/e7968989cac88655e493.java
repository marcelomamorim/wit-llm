/*
 * @test
 * @summary Test ConcurrentSkipListMap ceilingEntry method for null key and normal behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testCeilingEntryNullKey();
        testCeilingEntryNormal();
    }

    private static void testCeilingEntryNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.ceilingEntry(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testCeilingEntryNormal() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Map.Entry<Integer, String> e1 = map.ceilingEntry(15);
        if (e1 == null || !e1.getKey().equals(20) || !"twenty".equals(e1.getValue())) {
            throw new RuntimeException("ceilingEntry(15) did not return entry with key 20");
        }

        Map.Entry<Integer, String> e2 = map.ceilingEntry(20);
        if (e2 == null || !e2.getKey().equals(20) || !"twenty".equals(e2.getValue())) {
            throw new RuntimeException("ceilingEntry(20) did not return entry with key 20");
        }

        Map.Entry<Integer, String> e3 = map.ceilingEntry(35);
        if (e3 != null) {
            throw new RuntimeException("ceilingEntry(35) should return null when no ceiling key exists");
        }
    }
}
