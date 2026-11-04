/*
 * @test
 * @summary Test for ceilingKey method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testCeilingKeyWithNull();
        testCeilingKeyWithExistingKey();
        testCeilingKeyWithNonExistingKey();
        testCeilingKeyWithNullKey();
    }

    static void testCeilingKeyWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        try {
            Integer result = map.ceilingKey(null);
            if (result != null) {
                System.out.println("FAIL: Expected null for null key, got: " + result);
            } else {
                System.out.println("PASS: ceilingKey(null) returned null as expected");
            }
        } catch (NullPointerException e) {
            System.out.println("PASS: ceilingKey(null) threw NullPointerException as expected");
        } catch (Exception e) {
            System.out.println("FAIL: ceilingKey(null) threw unexpected exception: " + e);
        }
    }

    static void testCeilingKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Integer result = map.ceilingKey(1);
        if (result != null && result == 1) {
            System.out.println("PASS: ceilingKey(1) returned 1 as expected");
        } else {
            System.out.println("FAIL: ceilingKey(1) expected 1, got: " + result);
        }
    }

    static void testCeilingKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Integer result = map.ceilingKey(2);
        if (result != null && result == 3) {
            System.out.println("PASS: ceilingKey(2) returned 3 as expected");
        } else {
            System.out.println("FAIL: ceilingKey(2) expected 3, got: " + result);
        }
    }

    static void testCeilingKeyWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        try {
            map.ceilingKey(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: ceilingKey(null) threw NullPointerException as expected");
        } catch (Exception e) {
            System.out.println("FAIL: ceilingKey(null) threw unexpected exception: " + e);
        }
    }
}
