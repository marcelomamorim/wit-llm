/*
 * @test
 * @summary Test ConcurrentSkipListMap.floorKey(K) method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // floorKey on empty map returns null
        if (map.floorKey(10) != null) {
            throw new AssertionError("Expected null for floorKey on empty map");
        }

        // Add some keys
        map.put(5, "five");
        map.put(10, "ten");
        map.put(20, "twenty");

        // floorKey for exact key
        Integer fk = map.floorKey(10);
        if (fk == null || fk.intValue() != 10) {
            throw new AssertionError("Expected floorKey(10) to be 10, got " + fk);
        }

        // floorKey for key between existing keys
        fk = map.floorKey(15);
        if (fk == null || fk.intValue() != 10) {
            throw new AssertionError("Expected floorKey(15) to be 10, got " + fk);
        }

        // floorKey for key less than all keys
        fk = map.floorKey(1);
        if (fk != null) {
            throw new AssertionError("Expected floorKey(1) to be null, got " + fk);
        }

        // floorKey for key greater than all keys
        fk = map.floorKey(25);
        if (fk == null || fk.intValue() != 20) {
            throw new AssertionError("Expected floorKey(25) to be 20, got " + fk);
        }

        System.out.println("All tests passed.");
    }
}
