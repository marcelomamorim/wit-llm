/*
 * @test
 * @summary Witup-derived tests for ConcurrentSkipListMap.headMap(K)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testHeadMapIsExclusive();
        testEmptyMapHeadMap();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullKeyThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        boolean thrown = false;
        try {
            map.headMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "headMap(null) should throw NullPointerException");
    }

    static void testHeadMapIsExclusive() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        Map<Integer, String> head = map.headMap(3);
        check(head.containsKey(1), "headMap(3) should contain key 1");
        check(!head.containsKey(3), "headMap(3) should not contain key 3");
        check(!head.containsKey(5), "headMap(3) should not contain key 5");
        check(head.size() == 1, "headMap(3) size should be 1 but was " + head.size());
    }

    static void testEmptyMapHeadMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Map<Integer, String> head = map.headMap(10);
        check(head.isEmpty(), "headMap on empty map should be empty");
    }
}
