/*
 * @test
 * @summary Checks ConcurrentSkipListMap sub-map replace(key, oldValue, newValue) observable results
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ConcurrentSkipListMap<Integer, String> newMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 10; i++) {
            map.put(i, "v" + i);
        }
        return map;
    }

    static void testReplaceMatchingValueInRange() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, true, 7, true);
        check(sub.replace(5, "v5", "x5"), "replace should return true when old value matches");
        check("x5".equals(map.get(5)), "backing map should see replaced value");
    }

    static void testReplaceWrongOldValue() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, true, 7, true);
        check(!sub.replace(5, "wrong", "y"), "replace should return false when old value differs");
        check("v5".equals(map.get(5)), "value must remain unchanged after failed replace");
    }

    static void testReplaceAtBoundaryKeys() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, true, 7, true);
        check(sub.replace(3, "v3", "a3"), "replace at inclusive lower bound should succeed");
        check(sub.replace(7, "v7", "a7"), "replace at inclusive upper bound should succeed");
        check("a3".equals(map.get(3)), "lower bound value not updated");
        check("a7".equals(map.get(7)), "upper bound value not updated");
    }

    static void testReplaceAbsentKeyInRange() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        map.remove(6);
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, true, 7, true);
        check(!sub.replace(6, "v6", "q"), "replace on absent key should return false");
        check(!map.containsKey(6), "absent key must not be inserted by replace");
    }

    static void testReplaceDoesNotAffectOtherEntries() {
        ConcurrentSkipListMap<Integer, String> map = newMap();
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, true, 7, true);
        sub.replace(4, "v4", "z4");
        check("z4".equals(map.get(4)), "target entry not replaced");
        check("v2".equals(map.get(2)), "entry outside sub-map must be untouched");
        check("v8".equals(map.get(8)), "entry outside sub-map must be untouched");
        check(map.size() == 10, "size must not change on replace");
    }

    public static void main(String[] args) throws Exception {
        testReplaceMatchingValueInRange();
        testReplaceWrongOldValue();
        testReplaceAtBoundaryKeys();
        testReplaceAbsentKeyInRange();
        testReplaceDoesNotAffectOtherEntries();
    }
}
