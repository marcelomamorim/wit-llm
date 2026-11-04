/*
 * @test
 * @summary Verifies clear() on ConcurrentSkipListMap range views removes only the entries within the view range
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static ConcurrentSkipListMap<Integer, String> newMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 10; i++) {
            m.put(i, String.valueOf(i));
        }
        return m;
    }

    static List<Integer> keys(Map<Integer, String> m) {
        return new ArrayList<>(m.keySet());
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testHeadMapClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        ConcurrentNavigableMap<Integer, String> head = m.headMap(5);
        head.clear();
        check(keys(m).equals(Arrays.asList(5, 6, 7, 8, 9, 10)), "headMap(5).clear() wrong keys");
        check(m.size() == 6, "size after headMap clear");
    }

    static void testInclusiveSubMapClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        m.subMap(3, true, 7, true).clear();
        check(keys(m).equals(Arrays.asList(1, 2, 8, 9, 10)), "inclusive subMap clear wrong keys");
    }

    static void testExclusiveSubMapClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        m.subMap(3, false, 7, false).clear();
        check(keys(m).equals(Arrays.asList(1, 2, 3, 7, 8, 9, 10)), "exclusive subMap clear wrong keys");
    }

    static void testTailMapClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        m.tailMap(8).clear();
        check(keys(m).equals(Arrays.asList(1, 2, 3, 4, 5, 6, 7)), "tailMap(8).clear() wrong keys");
    }

    static void testEmptyRangeClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        m.subMap(20, true, 30, true).clear();
        check(m.size() == 10, "clear of range outside map must not remove entries");
    }

    static void testFullRangeViewClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        m.headMap(100).clear();
        check(m.isEmpty(), "headMap(100).clear() should empty the map");
    }

    static void testPutAfterViewClear() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        m.subMap(1, true, 10, true).clear();
        m.put(4, "x");
        check(keys(m).equals(Arrays.asList(4)), "map should accept put after view clear");
    }

    public static void main(String[] args) {
        testHeadMapClear();
        testInclusiveSubMapClear();
        testExclusiveSubMapClear();
        testTailMapClear();
        testEmptyRangeClear();
        testFullRangeViewClear();
        testPutAfterViewClear();
    }
}
