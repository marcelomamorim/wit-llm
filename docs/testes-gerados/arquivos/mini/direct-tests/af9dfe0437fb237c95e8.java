/*
 * @test
 * @summary Test ConcurrentSkipListMap descendingMap method correctness
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListMap.SubMap;
import java.util.Comparator;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        // Create a ConcurrentSkipListMap with natural ordering
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // Obtain the descending map view
        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> descMap = map.descendingMap();

        // Check that descendingMap is not null
        if (descMap == null) {
            throw new AssertionError("descendingMap() returned null");
        }

        // Check that descendingMap contains the same keys but in descending order
        Integer prevKey = null;
        for (Integer key : descMap.keySet()) {
            if (prevKey != null && key >= prevKey) {
                throw new AssertionError("Keys in descendingMap are not in descending order");
            }
            prevKey = key;
        }

        // Check that descendingMap contains all keys from original map
        for (Integer key : map.keySet()) {
            if (!descMap.containsKey(key)) {
                throw new AssertionError("descendingMap missing key: " + key);
            }
        }

        // Check that descendingMap values correspond to original map values
        for (Integer key : map.keySet()) {
            String originalValue = map.get(key);
            String descValue = descMap.get(key);
            if (descValue == null || !descValue.equals(originalValue)) {
                throw new AssertionError("Value mismatch for key " + key + ": expected " + originalValue + ", got " + descValue);
            }
        }

        // Check that descendingMap.descendingMap() returns a map equal to original map
        ConcurrentSkipListMap<Integer, String>.SubMap<Integer, String> doubleDescMap = descMap.descendingMap();
        if (doubleDescMap == null) {
            throw new AssertionError("double descendingMap() returned null");
        }

        // Check keys order in double descending map is ascending
        prevKey = null;
        for (Integer key : doubleDescMap.keySet()) {
            if (prevKey != null && key <= prevKey) {
                throw new AssertionError("Keys in double descendingMap are not in ascending order");
            }
            prevKey = key;
        }

        // Check that double descending map contains all keys from original map
        for (Integer key : map.keySet()) {
            if (!doubleDescMap.containsKey(key)) {
                throw new AssertionError("double descendingMap missing key: " + key);
            }
        }

        // Check values in double descending map
        for (Integer key : map.keySet()) {
            String originalValue = map.get(key);
            String doubleDescValue = doubleDescMap.get(key);
            if (doubleDescValue == null || !doubleDescValue.equals(originalValue)) {
                throw new AssertionError("Value mismatch in double descendingMap for key " + key + ": expected " + originalValue + ", got " + doubleDescValue);
            }
        }
    }
}
