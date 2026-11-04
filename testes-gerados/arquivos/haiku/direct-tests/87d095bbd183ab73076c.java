/*
 * @test
 * @summary Verifies containsValue on ConcurrentSkipListMap range views (headMap, tailMap, subMap) respects range bounds and rejects null values
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;
import java.util.SortedMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        m.put(4, "d");
        m.put(5, "e");
        return m;
    }

    static void testHeadMapBounds() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        SortedMap<Integer, String> head = m.headMap(3);
        check(head.containsValue("a"), "head should contain value of key 1");
        check(head.containsValue("b"), "head should contain value of key 2");
        check(!head.containsValue("c"), "head (exclusive toKey) must not contain value of key 3");
        check(!head.containsValue("e"), "head must not contain value of key 5");
    }

    static void testTailMapBounds() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        SortedMap<Integer, String> tail = m.tailMap(3);
        check(tail.containsValue("c"), "tail should contain value of fromKey 3 (inclusive)");
        check(tail.containsValue("e"), "tail should contain value of key 5");
        check(!tail.containsValue("a"), "tail must not contain value of key 1");
        check(!tail.containsValue("b"), "tail must not contain value of key 2");
    }

    static void testSubMapBounds() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        SortedMap<Integer, String> sub = m.subMap(2, 4);
        check(sub.containsValue("b"), "subMap should contain value of key 2");
        check(sub.containsValue("c"), "subMap should contain value of key 3");
        check(!sub.containsValue("a"), "subMap must not contain value of key 1");
        check(!sub.containsValue("d"), "subMap must not contain value of toKey 4");
    }

    static void testEqualsBasedMatch() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(10, "x");
        NavigableMap<Integer, String> view = m.tailMap(0, true);
        check(view.containsValue(new String("x")), "containsValue should match by equals");
        check(!view.containsValue("X"), "containsValue must be case sensitive via equals");
    }

    static void testEmptyRangeReturnsFalse() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        SortedMap<Integer, String> empty = m.subMap(10, 20);
        check(!empty.containsValue("a"), "empty range must not contain any value");
    }

    static void testNullValueThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        SortedMap<Integer, String> sub = m.subMap(1, 5);
        boolean thrown = false;
        try {
            sub.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsValue(null) on a submap must throw NullPointerException");
    }

    public static void main(String[] args) {
        testHeadMapBounds();
        testTailMapBounds();
        testSubMapBounds();
        testEqualsBasedMatch();
        testEmptyRangeReturnsFalse();
        testNullValueThrowsNPE();
    }
}
