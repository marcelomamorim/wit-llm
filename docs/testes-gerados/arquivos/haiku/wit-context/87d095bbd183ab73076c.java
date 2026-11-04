/*
 * @test
 * @summary Verifies that containsValue on ConcurrentSkipListMap range views rejects null and otherwise reports values only within the view range.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsOnHeadMap();
        testNullValueThrowsOnTailMap();
        testNullValueThrowsOnSubMap();
        testNullValueThrowsOnEmptyView();
        testContainsValueRespectsHeadMapRange();
        testContainsValueRespectsTailMapRange();
        testContainsValueRespectsSubMapRange();
        testContainsValueOnEmptyMapView();
    }

    private static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(4, "d");
        return map;
    }

    private static void expectNpe(ConcurrentNavigableMap<Integer, String> view, String name) {
        try {
            view.containsValue(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException(name + ": containsValue(null) did not throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testNullValueThrowsOnHeadMap() {
        expectNpe(sampleMap().headMap(3), "headMap");
    }

    private static void testNullValueThrowsOnTailMap() {
        expectNpe(sampleMap().tailMap(3), "tailMap");
    }

    private static void testNullValueThrowsOnSubMap() {
        expectNpe(sampleMap().subMap(2, 4), "subMap");
    }

    private static void testNullValueThrowsOnEmptyView() {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        expectNpe(empty.headMap(5), "empty headMap");
    }

    private static void testContainsValueRespectsHeadMapRange() {
        ConcurrentNavigableMap<Integer, String> view = sampleMap().headMap(3);
        check(view.containsValue("a"), "headMap should contain value of key 1");
        check(view.containsValue("b"), "headMap should contain value of key 2");
        check(!view.containsValue("c"), "headMap must not contain value of excluded key 3");
        check(!view.containsValue("d"), "headMap must not contain value of key 4");
    }

    private static void testContainsValueRespectsTailMapRange() {
        ConcurrentNavigableMap<Integer, String> view = sampleMap().tailMap(3);
        check(!view.containsValue("a"), "tailMap must not contain value of key 1");
        check(!view.containsValue("b"), "tailMap must not contain value of key 2");
        check(view.containsValue("c"), "tailMap should contain value of key 3");
        check(view.containsValue("d"), "tailMap should contain value of key 4");
    }

    private static void testContainsValueRespectsSubMapRange() {
        ConcurrentNavigableMap<Integer, String> view = sampleMap().subMap(2, 4);
        check(!view.containsValue("a"), "subMap must not contain value of key 1");
        check(view.containsValue("b"), "subMap should contain value of key 2");
        check(view.containsValue("c"), "subMap should contain value of key 3");
        check(!view.containsValue("d"), "subMap must not contain value of excluded key 4");
        check(!view.containsValue("zz"), "subMap must not report absent value");
    }

    private static void testContainsValueOnEmptyMapView() {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(!empty.headMap(5).containsValue("x"), "empty view must not contain any value");
    }
}
