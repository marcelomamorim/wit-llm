/*
 * @test    @summary Test headMap method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithNull();
        testHeadMapWithValidKey();
        testHeadMapWithKeyLessThanExisting();
        testHeadMapWithKeyGreaterThanExisting();
    }

    static void testHeadMapWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.headMap(null, true);
            System.out.println("FAIL: Expected NullPointerException for null toKey");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null toKey");
        }
    }

    static void testHeadMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentSkipListMap<Integer, String>.SubMap subMap = map.headMap(3, true);
        boolean containsKey2 = subMap.containsKey(2);
        boolean containsKey3 = subMap.containsKey(3);
        if (containsKey2 && containsKey3) {
            System.out.println("PASS: headMap includes keys less than or equal to 3");
        } else {
            System.out.println("FAIL: headMap does not include expected keys");
        }
    }

    static void testHeadMapWithKeyLessThanExisting() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        ConcurrentSkipListMap<Integer, String>.SubMap subMap = map.headMap(5, true);
        if (subMap.isEmpty()) {
            System.out.println("PASS: headMap with key less than smallest key is empty");
        } else {
            System.out.println("FAIL: headMap with key less than smallest key should be empty");
        }
    }

    static void testHeadMapWithKeyGreaterThanExisting() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentSkipListMap<Integer, String>.SubMap subMap = map.headMap(10, true);
        boolean containsKey1 = subMap.containsKey(1);
        boolean containsKey3 = subMap.containsKey(3);
        if (containsKey1 && containsKey3) {
            System.out.println("PASS: headMap includes all keys less than or equal to 10");
        } else {
            System.out.println("FAIL: headMap does not include expected keys");
        }
    }
}
