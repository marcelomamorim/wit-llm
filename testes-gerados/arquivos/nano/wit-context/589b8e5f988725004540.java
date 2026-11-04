/*
 * @test
 * @summary Test for higherKey method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHigherKeyWithNullKey();
        testHigherKeyWithExistingKey();
        testHigherKeyWithNonExistingKey();
    }

    private static void testHigherKeyWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        try {
            Integer result = map.higherKey(null);
            System.out.println("Test null key: Expected NullPointerException, but got: " + result);
        } catch (NullPointerException e) {
            System.out.println("Test null key: Passed (NullPointerException caught)");
        } catch (Exception e) {
            System.out.println("Test null key: Failed (Unexpected exception: " + e +)");
        }
    }

    private static void testHigherKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Integer result = map.higherKey(1);
        if (result != null && result == 3) {
            System.out.println("Test existing key: Passed");
        } else {
            System.out.println("Test existing key: Failed, got: " + result);
        }
    }

    private static void testHigherKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Integer result = map.higherKey(2);
        if (result != null && result == 3) {
            System.out.println("Test non-existing key: Passed");
        } else {
            System.out.println("Test non-existing key: Failed, got: " + result);
        }
    }
}
