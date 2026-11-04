/*
 * @test
 * @summary Witup-derived behavioral checks for ConcurrentSkipListMap.lastKey() and its submap/descending views
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        return map;
    }

    static void testEmptyMapLastKeyThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.lastKey();
            throw new RuntimeException("expected NoSuchElementException for empty map");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testLastKeyReturnsMaximum() {
        check(sampleMap().lastKey().equals(3), "lastKey should return 3");
    }

    static void testHeadMapExclusiveLastKey() {
        ConcurrentNavigableMap<Integer, String> head = sampleMap().headMap(2);
        check(head.lastKey().equals(1), "headMap(2).lastKey should be 1");
    }

    static void testHeadMapInclusiveLastKey() {
        ConcurrentNavigableMap<Integer, String> head = sampleMap().headMap(2, true);
        check(head.lastKey().equals(2), "headMap(2,true).lastKey should be 2");
    }

    static void testHeadMapExclusiveEmptyRangeThrows() {
        ConcurrentNavigableMap<Integer, String> head = sampleMap().headMap(1);
        try {
            head.lastKey();
            throw new RuntimeException("expected NoSuchElementException for empty exclusive head view");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testHeadMapInclusiveSingleElement() {
        ConcurrentNavigableMap<Integer, String> head = sampleMap().headMap(1, true);
        check(head.lastKey().equals(1), "headMap(1,true).lastKey should be 1");
    }

    static void testTailMapLastKey() {
        ConcurrentNavigableMap<Integer, String> tail = sampleMap().tailMap(2);
        check(tail.lastKey().equals(3), "tailMap(2).lastKey should be 3");
    }

    static void testDescendingMapLastKeyIsFirstKey() {
        ConcurrentNavigableMap<Integer, String> desc = sampleMap().descendingMap();
        check(desc.lastKey().equals(1), "descendingMap().lastKey should be 1");
    }

    static void testDescendingEmptyMapThrows() {
        ConcurrentNavigableMap<Integer, String> desc =
            new ConcurrentSkipListMap<Integer, String>().descendingMap();
        try {
            desc.lastKey();
            throw new RuntimeException("expected NoSuchElementException for empty descending view");
        } catch (NoSuchElementException expected) {
        }
    }

    public static void main(String[] args) {
        testEmptyMapLastKeyThrows();
        testLastKeyReturnsMaximum();
        testHeadMapExclusiveLastKey();
        testHeadMapInclusiveLastKey();
        testHeadMapExclusiveEmptyRangeThrows();
        testHeadMapInclusiveSingleElement();
        testTailMapLastKey();
        testDescendingMapLastKeyIsFirstKey();
        testDescendingEmptyMapThrows();
    }
}
