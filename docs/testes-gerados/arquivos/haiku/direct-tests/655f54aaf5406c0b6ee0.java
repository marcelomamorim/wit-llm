/*
 * @test
 * @summary headMap(K) returns the strict head view of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHeadIsExclusiveOfToKey();
        testEmptyMap();
        testBoundsBelowAndAboveAllKeys();
        testReverseComparatorOrdering();
        testViewReflectsLaterPuts();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sample() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, String.valueOf(i));
        }
        return m;
    }

    static void testHeadIsExclusiveOfToKey() {
        ConcurrentSkipListMap<Integer, String> m = sample();
        SortedMap<Integer, String> h = m.headMap(3);
        List<Integer> keys = new ArrayList<>(h.keySet());
        check(keys.equals(Arrays.asList(1, 2)), "expected keys [1, 2] but got " + keys);
        check(h.size() == 2, "expected size 2 but got " + h.size());
        check(!h.containsKey(3), "toKey must be excluded from head view");
        check(h.containsKey(2), "key just below toKey must be present");
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        SortedMap<Integer, String> h = m.headMap(10);
        check(h.isEmpty(), "head of empty map must be empty");
        check(h.size() == 0, "size of head of empty map must be 0");
    }

    static void testBoundsBelowAndAboveAllKeys() {
        ConcurrentSkipListMap<Integer, String> m = sample();
        check(m.headMap(0).isEmpty(), "no key is less than 0");
        check(m.headMap(1).isEmpty(), "no key is less than 1");
        check(m.headMap(100).size() == 5, "all keys are less than 100");
    }

    static void testReverseComparatorOrdering() {
        ConcurrentSkipListMap<Integer, String> m =
            new ConcurrentSkipListMap<Integer, String>(Collections.<Integer>reverseOrder());
        for (int i = 1; i <= 5; i++) {
            m.put(i, String.valueOf(i));
        }
        SortedMap<Integer, String> h = m.headMap(3);
        List<Integer> keys = new ArrayList<>(h.keySet());
        check(keys.equals(Arrays.asList(5, 4)),
              "with reverse order, head of 3 must be [5, 4] but got " + keys);
    }

    static void testViewReflectsLaterPuts() {
        ConcurrentSkipListMap<Integer, String> m = sample();
        SortedMap<Integer, String> h = m.headMap(3);
        m.put(0, "zero");
        List<Integer> keys = new ArrayList<>(h.keySet());
        check(keys.equals(Arrays.asList(0, 1, 2)),
              "head view must reflect new mapping 0 but got " + keys);
    }
}
