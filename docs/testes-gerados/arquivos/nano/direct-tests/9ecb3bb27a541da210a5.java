/*
 * @test
 * @summary Test for floorKey method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFloorKeyWithExistingKey();
        testFloorKeyWithNonExistingKey();
        testFloorKeyWithNullKey();
    }

    private static void testFloorKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.floorKey(20);
        if (result == null || result != 20) {
            throw new AssertionError("Expected floorKey(20) to be 20, but got: " + result);
        }
    }

    private static void testFloorKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.floorKey(25);
        if (result == null || result != 20) {
            throw new AssertionError("Expected floorKey(25) to be 20, but got: " + result);
        }
    }

    private static void testFloorKeyWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        try {
            map.floorKey(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
