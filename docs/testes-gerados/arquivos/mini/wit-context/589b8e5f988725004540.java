/*
 * @test
 * @summary Test ConcurrentSkipListMap.higherKey(K) for null key and normal keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // Test higherKey with keys present and absent
        Integer hk1 = map.higherKey(1);
        if (hk1 == null || hk1.intValue() != 3) {
            throw new AssertionError("Expected higherKey(1) to be 3 but got " + hk1);
        }

        Integer hk2 = map.higherKey(2);
        if (hk2 == null || hk2.intValue() != 3) {
            throw new AssertionError("Expected higherKey(2) to be 3 but got " + hk2);
        }

        Integer hk3 = map.higherKey(5);
        if (hk3 != null) {
            throw new AssertionError("Expected higherKey(5) to be null but got " + hk3);
        }

        // Test higherKey with null key triggers NullPointerException
        try {
            map.higherKey(null);
            throw new AssertionError("Expected NullPointerException for higherKey(null)");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
