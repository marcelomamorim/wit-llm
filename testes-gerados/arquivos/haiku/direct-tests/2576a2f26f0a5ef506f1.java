/*
 * @test
 * @summary ConcurrentSkipListMap.subMap(fromKey, toKey) returns a half-open view [fromKey, toKey)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRangeContents();
        testBoundaries();
        testViewWriteThroughToBackingMap();
        testBackingMapWriteVisibleInView();
        testEmptyRange();
        testFullRange();
    }

    static ConcurrentSkipListMap<Integer, String> newMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 10; i++) {
            map.put(i, "v" + i);
        }
        return map;
    }

    static void testRangeContents() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, 7);
        check(sub.size() == 4, "size of subMap(3,7) should be 4");
        check(sub.firstKey() == 3, "firstKey should be 3");
        check(sub.lastKey() == 6, "lastKey should be 6");
        List<Integer> keys = new ArrayList<>(sub.keySet());
        check(keys.equals(Arrays.asList(3, 4, 5, 6)), "keys should be [3,4,5,6] but were " + keys);
        check("v4".equals(sub.get(4)), "get(4) should return v4");
    }

    static void testBoundaries() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, 7);
        check(sub.containsKey(3), "fromKey must be included");
        check(sub.containsKey(6), "key just below toKey must be included");
        check(!sub.containsKey(7), "toKey must be excluded");
        check(!sub.containsKey(2), "key below fromKey must be excluded");
    }

    static void testViewWriteThroughToBackingMap() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, 7);
        sub.remove(4);
        check(!map.containsKey(4), "removal through view should affect backing map");
        check(sub.size() == 3, "view size should be 3 after removal");
        check(map.size() == 9, "backing map size should be 9 after removal");
    }

    static void testBackingMapWriteVisibleInView() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, 7);
        map.put(5, "x");
        check("x".equals(sub.get(5)), "update in backing map should be visible in view");
        map.remove(3);
        check(sub.size() == 3, "removal from backing map should be visible in view");
    }

    static void testEmptyRange() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(5, 5);
        check(sub.isEmpty(), "subMap(5,5) should be empty");
        check(sub.size() == 0, "size of empty range should be 0");
    }

    static void testFullRange() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, 11);
        check(sub.size() == 10, "subMap(1,11) should contain all 10 keys");
        check(sub.containsKey(10), "key 10 should be included");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
