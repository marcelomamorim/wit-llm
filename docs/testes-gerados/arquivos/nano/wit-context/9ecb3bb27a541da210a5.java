/*
 * @test
 * @summary Test for ConcurrentSkipListMap.floorKey handling null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFloorKeyWithNull();
        testFloorKeyWithExistingKey();
        testFloorKeyWithAbsentKey();
    }

    static void testFloorKeyWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.floorKey(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testFloorKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        Integer result = map.floorKey(15);
        if (result != null && result == 10) {
            System.out.println("PASS: floorKey(15) == 10");
        } else {
            System.out.println("FAIL: floorKey(15) != 10, got: " + result);
        }
    }

    static void testFloorKeyWithAbsentKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        Integer result = map.floorKey(25);
        if (result != null && result == 20) {
            System.out.println("PASS: floorKey(25) == 20");
        } else {
            System.out.println("FAIL: floorKey(25) != 20, got: " + result);
        }
    }
}
