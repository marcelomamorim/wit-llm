/*
 * @test
 * @summary Test ConcurrentSkipListMap.lowerKey(K) method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test lowerKey on empty map returns null
        if (map.lowerKey(10) != null) {
            throw new AssertionError("Expected null for lowerKey on empty map");
        }

        // Populate map with some keys
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        // Test lowerKey for key present in map returns immediate lower key
        Integer lk = map.lowerKey(20);
        if (lk == null || !lk.equals(10)) {
            throw new AssertionError("Expected lowerKey(20) to be 10, got " + lk);
        }

        // Test lowerKey for key not present returns greatest key less than given key
        lk = map.lowerKey(25);
        if (lk == null || !lk.equals(20)) {
            throw new AssertionError("Expected lowerKey(25) to be 20, got " + lk);
        }

        // Test lowerKey for key less than all keys returns null
        lk = map.lowerKey(5);
        if (lk != null) {
            throw new AssertionError("Expected lowerKey(5) to be null, got " + lk);
        }

        // Test lowerKey for key greater than all keys returns greatest key
        lk = map.lowerKey(40);
        if (lk == null || !lk.equals(30)) {
            throw new AssertionError("Expected lowerKey(40) to be 30, got " + lk);
        }

        System.out.println("All tests passed.");
    }
}
