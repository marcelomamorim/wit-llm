/*
 * @test
 * @summary Test ConcurrentSkipListMap.floorKey(K) for null key and normal keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();

        // Test floorKey with null key - expect NullPointerException
        boolean npeThrown = false;
        try {
            map.floorKey(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for floorKey(null)");
        }

        // Test floorKey with empty map - expect null result
        String result = map.floorKey("a");
        if (result != null) {
            throw new AssertionError("Expected null for floorKey on empty map");
        }

        // Add some keys
        map.put("a", 1);
        map.put("c", 3);
        map.put("e", 5);

        // floorKey for exact key
        if (!"a".equals(map.floorKey("a"))) {
            throw new AssertionError("floorKey('a') should return 'a'");
        }

        // floorKey for key between 'a' and 'c' should return 'a'
        if (!"a".equals(map.floorKey("b"))) {
            throw new AssertionError("floorKey('b') should return 'a'");
        }

        // floorKey for key greater than highest key 'e' should return 'e'
        if (!"e".equals(map.floorKey("z"))) {
            throw new AssertionError("floorKey('z') should return 'e'");
        }

        // floorKey for key less than lowest key 'a' should return null
        if (map.floorKey("0") != null) {
            throw new AssertionError("floorKey('0') should return null");
        }
    }
}
