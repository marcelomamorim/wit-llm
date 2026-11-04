/*
 * @test
 * @summary Test lowerKey method of ConcurrentSkipListMap for correct behavior.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testLowerKeyWithExistingKey();
        testLowerKeyWithNonExistingKey();
        testLowerKeyWithNullKey();
    }

    static void testLowerKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.lowerKey(25);
        assert result != null && result == 20 : "Expected lowerKey of 25 to be 20, but got " + result;
    }

    static void testLowerKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Integer result = map.lowerKey(10);
        assert result == null : "Expected lowerKey of 10 to be null, but got " + result;
    }

    static void testLowerKeyWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        try {
            map.lowerKey(null);
            assert false : "Expected NullPointerException when calling lowerKey with null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
