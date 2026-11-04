/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.headMap method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithNullToKey();
        testHeadMapWithValidKey();
    }

    private static void testHeadMapWithNullToKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean threwException = false;
        try {
            map.headMap(null, true);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when toKey is null");
        }
    }

    private static void testHeadMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentSkipListMap.SubMap<Integer, String> subMap = map.headMap(3, true);
        if (subMap == null) {
            throw new AssertionError("headMap returned null for valid toKey");
        }
        // Verify that subMap contains keys less than or equal to 3
        for (Map.Entry<Integer, String> entry : subMap.entrySet()) {
            if (entry.getKey() > 3) {
                throw new AssertionError("SubMap contains key greater than toKey");
            }
        }
        // Verify that subMap does not contain keys greater than 3
        if (subMap.containsKey(4)) {
            throw new AssertionError("SubMap contains key greater than toKey");
        }
    }
}
