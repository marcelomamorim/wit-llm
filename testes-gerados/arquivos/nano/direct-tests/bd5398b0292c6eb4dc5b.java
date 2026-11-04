/*
 * @test    @summary Test tailMap method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testTailMapWithNullKey();
        testTailMapWithValidKey();
        testTailMapWithNonExistingKey();
    }

    static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.tailMap(null, true);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testTailMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");
        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> subMap = map.tailMap(3, true);
        boolean containsKey3 = subMap.containsKey(3);
        boolean containsKey5 = subMap.containsKey(5);
        boolean containsKey1 = subMap.containsKey(1);
        if (containsKey3 && containsKey5 && !containsKey1) {
            System.out.println("PASS: tailMap with valid key includes keys >= 3");
        } else {
            System.out.println("FAIL: tailMap with valid key does not behave as expected");
        }
    }

    static void testTailMapWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> subMap = map.tailMap(15, false);
        boolean containsKey10 = subMap.containsKey(10);
        boolean containsKey20 = subMap.containsKey(20);
        if (!containsKey10 && containsKey20) {
            System.out.println("PASS: tailMap with non-existing key returns subset starting from key 15");
        } else {
            System.out.println("FAIL: tailMap with non-existing key does not behave as expected");
        }
    }
}
