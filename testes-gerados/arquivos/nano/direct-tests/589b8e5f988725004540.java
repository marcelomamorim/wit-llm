/*
 * @test    @summary Test higherKey method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHigherKeyWithExistingKey();
        testHigherKeyWithNonExistingKey();
        testHigherKeyWithNullKey();
    }

    static void testHigherKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");
        Integer result = map.higherKey(20);
        if (result == null || result != 30) {
            throw new AssertionError("Expected higherKey(20) to be 30, but got: " + result);
        }
    }

    static void testHigherKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");
        Integer result = map.higherKey(25);
        if (result == null || result != 30) {
            throw new AssertionError("Expected higherKey(25) to be 30, but got: " + result);
        }
    }

    static void testHigherKeyWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");
        try {
            map.higherKey(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
