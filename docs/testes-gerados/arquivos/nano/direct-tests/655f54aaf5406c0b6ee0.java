/*
 * @test
 * @summary Test headMap method of ConcurrentSkipListMap for correctness and regressions
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithNullKey();
        testHeadMapWithExistingKey();
        testHeadMapWithKeyLessThanAll();
        testHeadMapWithKeyGreaterThanAll();
    }

    static void testHeadMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.headMap(null);
            System.out.println("testHeadMapWithNullKey: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testHeadMapWithNullKey: PASSED");
        }
    }

    static void testHeadMapWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        var subMap = map.headMap(3);
        if (subMap.size() == 2 && subMap.containsKey(1) && subMap.containsKey(2)) {
            System.out.println("testHeadMapWithExistingKey: PASSED");
        } else {
            System.out.println("testHeadMapWithExistingKey: FAILED");
        }
    }

    static void testHeadMapWithKeyLessThanAll() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        var subMap = map.headMap(5);
        if (subMap.isEmpty()) {
            System.out.println("testHeadMapWithKeyLessThanAll: PASSED");
        } else {
            System.out.println("testHeadMapWithKeyLessThanAll: FAILED");
        }
    }

    static void testHeadMapWithKeyGreaterThanAll() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        var subMap = map.headMap(100);
        if (subMap.size() == 2 && subMap.containsKey(1) && subMap.containsKey(2)) {
            System.out.println("testHeadMapWithKeyGreaterThanAll: PASSED");
        } else {
            System.out.println("testHeadMapWithKeyGreaterThanAll: FAILED");
        }
    }
}
