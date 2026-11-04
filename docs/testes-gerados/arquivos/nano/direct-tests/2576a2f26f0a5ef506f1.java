/*
 * @test    @summary Test subMap method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testSubMapBasic();
        testSubMapEmpty();
        testSubMapBoundary();
    }

    static void testSubMapBasic() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");

        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> subMap = map.subMap(2, 5);
        boolean containsKey2 = subMap.containsKey(2);
        boolean containsKey4 = subMap.containsKey(4);
        boolean containsKey5 = subMap.containsKey(5);

        if (!containsKey2 || !containsKey4 || containsKey5) {
            throw new AssertionError("subMap basic test failed");
        }
    }

    static void testSubMapEmpty() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> subMap = map.subMap(40, 50);
        if (subMap.size() != 0) {
            throw new AssertionError("subMap empty test failed");
        }
    }

    static void testSubMapBoundary() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");

        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> subMap = map.subMap(2, 4);
        boolean hasKey2 = subMap.containsKey(2);
        boolean hasKey3 = subMap.containsKey(3);
        boolean hasKey4 = subMap.containsKey(4);

        if (!hasKey2 || !hasKey3 || hasKey4) {
            throw new AssertionError("subMap boundary test failed");
        }
    }
}
