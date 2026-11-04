/*
 * @test
 * @summary putIfAbsent on a ConcurrentSkipListMap sub-map view respects key bounds, null checks and absent-key semantics
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(5, "e");
        map.put(9, "i");

        NavigableMap<Integer, String> sub = map.subMap(3, true, 8, true);

        // Absent key inside bounds: inserted and returns null
        check(sub.putIfAbsent(4, "d") == null, "absent in-range key should return null");
        check("d".equals(map.get(4)), "absent in-range key should be inserted in backing map");

        // Present key inside bounds: existing value returned, mapping unchanged
        check("e".equals(sub.putIfAbsent(5, "X")), "present key should return existing value");
        check("e".equals(map.get(5)), "present key value must not be replaced");

        // Key outside the sub-map range must be rejected and leave the map unchanged
        boolean threw = false;
        try {
            sub.putIfAbsent(9, "x");
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        check(threw, "out-of-range key should throw IllegalArgumentException");
        check("i".equals(map.get(9)), "out-of-range key must not modify backing map");

        // Null key must be rejected
        threw = false;
        try {
            sub.putIfAbsent(null, "x");
        } catch (NullPointerException expected) {
            threw = true;
        }
        check(threw, "null key should throw NullPointerException");

        // Null value must be rejected by the backing map
        threw = false;
        try {
            sub.putIfAbsent(6, null);
        } catch (NullPointerException expected) {
            threw = true;
        }
        check(threw, "null value should throw NullPointerException");
        check(!map.containsKey(6), "null value must not insert a mapping");
    }
}
