/*
 * @test
 * @summary Test ConcurrentSkipListMap.higherKey(K key) method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test higherKey on empty map returns null
        if (map.higherKey(10) != null) {
            throw new AssertionError("Expected null for higherKey on empty map");
        }

        // Insert some keys
        map.put(5, "five");
        map.put(10, "ten");
        map.put(15, "fifteen");

        // Test higherKey returns the least key strictly greater than given key
        Integer hk = map.higherKey(5);
        if (hk == null || hk != 10) {
            throw new AssertionError("Expected higherKey(5) to be 10, got " + hk);
        }

        hk = map.higherKey(10);
        if (hk == null || hk != 15) {
            throw new AssertionError("Expected higherKey(10) to be 15, got " + hk);
        }

        // Test higherKey with key greater than any in map returns null
        hk = map.higherKey(20);
        if (hk != null) {
            throw new AssertionError("Expected null for higherKey(20), got " + hk);
        }

        // Test higherKey with key between existing keys
        hk = map.higherKey(7);
        if (hk == null || hk != 10) {
            throw new AssertionError("Expected higherKey(7) to be 10, got " + hk);
        }

        // Test higherKey with key equal to highest key returns null
        hk = map.higherKey(15);
        if (hk != null) {
            throw new AssertionError("Expected null for higherKey(15), got " + hk);
        }
    }
}
