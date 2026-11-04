/*
 * @test
 * @summary Test ceilingKey method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testCeilingKeyWithExistingKey();
        testCeilingKeyWithNonExistingKey();
        testCeilingKeyWithNull();
    }

    static void testCeilingKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.ceilingKey(15);
        if (result == null || result != 20) {
            throw new AssertionError("Expected ceilingKey(15) to be 20, but got " + result);
        }
    }

    static void testCeilingKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        map.put(25, "twenty five");
        Integer result = map.ceilingKey(26);
        if (result != null) {
            throw new AssertionError("Expected ceilingKey(26) to be null, but got " + result);
        }
    }

    static void testCeilingKeyWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            map.ceilingKey(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
