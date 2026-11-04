/*
 * @test
 * @summary Verify replace(K, V) on ConcurrentSkipListMap and on its sub-map views
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceExistingKeyOnMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");

        String previous = map.replace(2, "B");
        check("b".equals(previous), "replace should return previous value, got " + previous);
        check("B".equals(map.get(2)), "value should be updated to B");
        check(map.size() == 3, "size should remain 3, got " + map.size());
    }

    static void testReplaceAbsentKeyDoesNotInsert() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");

        String previous = map.replace(9, "x");
        check(previous == null, "replace of absent key should return null");
        check(!map.containsKey(9), "replace must not insert absent key");
        check(map.size() == 2, "size should remain 2, got " + map.size());
    }

    static void testReplaceThroughSubMapView() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");

        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 3, true);

        String prev2 = sub.replace(2, "X");
        check("b".equals(prev2), "sub-map replace should return previous value, got " + prev2);
        check("X".equals(map.get(2)), "backing map should reflect sub-map replace");

        String prev3 = sub.replace(3, "Z");
        check("c".equals(prev3), "sub-map replace at bound should return previous value, got " + prev3);
        check("Z".equals(map.get(3)), "backing map should reflect bound replace");
    }

    static void testReplaceAbsentKeyThroughSubMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");

        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 3, true);
        String previous = sub.replace(5, "none");
        check(previous == null, "absent in-range-free key replace should yield null for present-range map");
        check(!map.containsKey(5), "sub-map replace must not insert absent key into backing map");
    }

    public static void main(String[] args) {
        testReplaceExistingKeyOnMap();
        testReplaceAbsentKeyDoesNotInsert();
        testReplaceThroughSubMapView();
        testReplaceAbsentKeyThroughSubMap();
    }
}
