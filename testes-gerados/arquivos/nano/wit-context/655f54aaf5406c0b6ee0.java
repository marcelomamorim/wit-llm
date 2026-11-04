/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.headMap(K)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithNullKey();
        testHeadMapWithValidKey();
    }

    private static void testHeadMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.headMap(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testHeadMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            ConcurrentSkipListMap.SubMap<Integer, String> subMap = map.headMap(3);
            if (subMap != null && subMap.size() == 2) {
                System.out.println("PASS: headMap with valid key returns expected submap");
            } else {
                System.out.println("FAIL: headMap with valid key returned unexpected result");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
