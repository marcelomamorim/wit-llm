/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.floorEntry null key handling and floor semantics
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFloorEntryNullKeyOnEmptyMap();
        testFloorEntryNullKeyOnPopulatedMap();
        testFloorEntryNullKeyWithComparator();
        testFloorEntryFloorSemantics();
        testFloorEntryEmptyMapReturnsNull();
    }

    static void testFloorEntryNullKeyOnEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        expectNpe(map, null);
    }

    static void testFloorEntryNullKeyOnPopulatedMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        expectNpe(map, null);
    }

    static void testFloorEntryNullKeyWithComparator() {
        ConcurrentSkipListMap<Integer, String> map =
            new ConcurrentSkipListMap<>(java.util.Comparator.reverseOrder());
        map.put(10, "a");
        expectNpe(map, null);
    }

    static void testFloorEntryFloorSemantics() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");

        check(map.floorEntry(25), 20, "b");
        check(map.floorEntry(20), 20, "b");
        check(map.floorEntry(30), 30, "c");
        check(map.floorEntry(100), 30, "c");

        if (map.floorEntry(5) != null) {
            throw new RuntimeException("floorEntry below minimum should be null");
        }
    }

    static void testFloorEntryEmptyMapReturnsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        if (map.floorEntry(1) != null) {
            throw new RuntimeException("floorEntry on empty map should be null");
        }
    }

    static void expectNpe(ConcurrentSkipListMap<Integer, String> map, Integer key) {
        try {
            map.floorEntry(key);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null key");
    }

    static void check(Map.Entry<Integer, String> e, int expectedKey, String expectedValue) {
        if (e == null) {
            throw new RuntimeException("unexpected null entry");
        }
        if (e.getKey().intValue() != expectedKey || !expectedValue.equals(e.getValue())) {
            throw new RuntimeException("unexpected floor entry: " + e.getKey() + "=" + e.getValue());
        }
    }
}
