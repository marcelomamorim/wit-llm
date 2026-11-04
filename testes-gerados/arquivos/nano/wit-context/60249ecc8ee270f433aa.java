/*
 * @test
 * @summary Test for lowerKey method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testLowerKeyWithNull();
        testLowerKeyWithExistingKey();
        testLowerKeyWithNonExistingKey();
    }

    private static void testLowerKeyWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.lowerKey(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testLowerKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.lowerKey(20);
        if (result != null && result == 10) {
            System.out.println("PASS: lowerKey(20) returned 10 as expected");
        } else {
            System.out.println("FAIL: lowerKey(20) returned " + result);
        }
    }

    private static void testLowerKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.lowerKey(25);
        if (result != null && result == 20) {
            System.out.println("PASS: lowerKey(25) returned 20 as expected");
        } else {
            System.out.println("FAIL: lowerKey(25) returned " + result);
        }
    }
}
