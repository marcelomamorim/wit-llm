/*
 * @test
 * @summary Checks headMap(K, boolean) null guard and public submap behaviour of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        checkNullToKeyThrowsNPE();
        checkHeadMapExclusive();
        checkHeadMapInclusive();
        checkEmptyMapHeadMap();
    }

    static ConcurrentSkipListMap<Integer, String> populated() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 10; i++) {
            map.put(i, "v" + i);
        }
        return map;
    }

    static void checkNullToKeyThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = populated();
        try {
            map.headMap(null, true);
            throw new AssertionError("expected NullPointerException for inclusive=true");
        } catch (NullPointerException expected) {
            // expected
        }
        try {
            map.headMap(null, false);
            throw new AssertionError("expected NullPointerException for inclusive=false");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void checkHeadMapExclusive() {
        ConcurrentSkipListMap<Integer, String> map = populated();
        Map<Integer, String> sub = map.headMap(5, false);
        check(sub.size() == 4, "headMap(5,false) size expected 4 but was " + sub.size());
        check(sub.containsKey(1) && sub.containsKey(4), "keys 1..4 should be in headMap(5,false)");
        check(!sub.containsKey(5), "key 5 must be excluded from headMap(5,false)");
        check(!sub.containsKey(6), "key 6 must not be in headMap(5,false)");
    }

    static void checkHeadMapInclusive() {
        ConcurrentSkipListMap<Integer, String> map = populated();
        Map<Integer, String> sub = map.headMap(5, true);
        check(sub.size() == 5, "headMap(5,true) size expected 5 but was " + sub.size());
        check(sub.containsKey(5), "key 5 must be included in headMap(5,true)");
        check(!sub.containsKey(6), "key 6 must not be in headMap(5,true)");
    }

    static void checkEmptyMapHeadMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Map<Integer, String> sub = map.headMap(5, true);
        check(sub.isEmpty(), "headMap on empty map must be empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
