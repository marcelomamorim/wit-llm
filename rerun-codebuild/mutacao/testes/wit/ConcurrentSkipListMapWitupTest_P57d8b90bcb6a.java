/*
 * @test
 * @summary Test tailMap method for ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P57d8b90bcb6a
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;

public class ConcurrentSkipListMapWitupTest_P57d8b90bcb6a {

    public static void main(String[] args) {
        testTailMapWithNullKey();
        testTailMapWithValidKey();
    }

    static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.tailMap(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    static void testTailMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        NavigableMap<Integer, String> tailMap = map.tailMap(2);
        if (tailMap.size() == 2 && tailMap.containsKey(2) && tailMap.containsKey(3)) {
            System.out.println("PASS: tailMap with key 2 contains correct entries");
        } else {
            System.out.println("FAIL: tailMap with key 2 does not contain expected entries");
        }
    }
}
