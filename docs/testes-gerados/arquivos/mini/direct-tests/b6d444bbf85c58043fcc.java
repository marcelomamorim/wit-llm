/*
 * @test
 * @summary Test ConcurrentSkipListMap ceilingKey method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test ceilingKey on empty map returns null
        if (map.ceilingKey(10) != null) {
            throw new AssertionError("ceilingKey on empty map should return null");
        }

        // Add some keys
        map.put(5, "five");
        map.put(10, "ten");
        map.put(20, "twenty");

        // Test ceilingKey with key less than lowest key
        Integer ck = map.ceilingKey(1);
        if (ck == null || ck.intValue() != 5) {
            throw new AssertionError("ceilingKey(1) should return 5, got " + ck);
        }

        // Test ceilingKey with key equal to a key
        ck = map.ceilingKey(10);
        if (ck == null || ck.intValue() != 10) {
            throw new AssertionError("ceilingKey(10) should return 10, got " + ck);
        }

        // Test ceilingKey with key between keys
        ck = map.ceilingKey(15);
        if (ck == null || ck.intValue() != 20) {
            throw new AssertionError("ceilingKey(15) should return 20, got " + ck);
        }

        // Test ceilingKey with key greater than highest key
        ck = map.ceilingKey(25);
        if (ck != null) {
            throw new AssertionError("ceilingKey(25) should return null, got " + ck);
        }

        System.out.println("All tests passed.");
    }
}
