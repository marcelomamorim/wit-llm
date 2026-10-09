/*
 * @test
 * @summary Test ConcurrentSkipListMap.tailMap(K) method behavior
 * @run main ConcurrentSkipListMapWitupTest_P439c946d0cb7
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class ConcurrentSkipListMapWitupTest_P439c946d0cb7 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Initially empty map: tailMap should be empty
        ConcurrentNavigableMap<Integer, String> tail = map.tailMap(10);
        if (!tail.isEmpty()) {
            throw new AssertionError("tailMap of empty map should be empty");
        }

        // Add some entries
        map.put(5, "five");
        map.put(10, "ten");
        map.put(15, "fifteen");

        // tailMap from 10 inclusive should contain keys 10 and 15
        tail = map.tailMap(10);
        if (tail.size() != 2 || !tail.containsKey(10) || !tail.containsKey(15)) {
            throw new AssertionError("tailMap(10) should contain keys 10 and 15");
        }

        // tailMap from 5 inclusive should contain keys 5, 10, 15
        tail = map.tailMap(5);
        if (tail.size() != 3 || !tail.containsKey(5) || !tail.containsKey(10) || !tail.containsKey(15)) {
            throw new AssertionError("tailMap(5) should contain keys 5, 10 and 15");
        }

        // tailMap from 20 inclusive should be empty
        tail = map.tailMap(20);
        if (!tail.isEmpty()) {
            throw new AssertionError("tailMap(20) should be empty");
        }

        // tailMap with null key should throw NullPointerException
        boolean npeThrown = false;
        try {
            map.tailMap(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("tailMap(null) should throw NullPointerException");
        }
    }
}
