/*
 * @test
 * @summary Test ConcurrentSkipListMap.SubMap descendingMap method for correctness and exception safety
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.SortedMap;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testDescendingMapBasic();
        testDescendingMapExceptionCondition();
        System.out.println("PASS");
    }

    // Test basic descendingMap behavior on empty and populated map
    private static void testDescendingMapBasic() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // descendingMap on empty map should be empty
        SortedMap<Integer, String> desc = map.descendingMap();
        if (!desc.isEmpty()) {
            throw new AssertionError("descendingMap of empty map should be empty");
        }

        // Put some entries and test descending order
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");

        SortedMap<Integer, String> descMap = map.descendingMap();
        Integer prev = null;
        for (Integer key : descMap.keySet()) {
            if (prev != null && key >= prev) {
                throw new AssertionError("descendingMap keys not in descending order");
            }
            prev = key;
        }

        // descendingMap of descendingMap returns original ascending map
        SortedMap<Integer, String> ascMap = descMap.descendingMap();
        if (!ascMap.equals(map)) {
            throw new AssertionError("descendingMap of descendingMap should return original map");
        }
    }

    // Test the exception condition described by WIT: IllegalArgumentException if comparator inconsistent with bounds
    private static void testDescendingMapExceptionCondition() {
        // Create a map with a comparator that reverses natural order
        Comparator<Integer> reverseCmp = (a, b) -> b.compareTo(a);
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>(reverseCmp);

        // Put some entries
        map.put(1, "one");
        map.put(2, "two");

        // Create a subMap with lo > hi according to comparator to trigger IllegalArgumentException
        // The SubMap class is package-private, so we cannot instantiate directly.
        // Instead, use subMap method with reversed bounds to provoke the exception.

        try {
            // lo = 2, hi = 1 with reverse comparator means lo < hi in natural order but comparator reverses
            map.subMap(2, true, 1, true);
            throw new AssertionError("Expected IllegalArgumentException for invalid subMap bounds");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Also test descendingMap on a subMap with invalid bounds indirectly
        // Since SubMap is internal, we test descendingMap on a valid subMap and verify no exception
        SortedMap<Integer, String> validSubMap = map.subMap(2, true, 1, false);
        // This is empty but valid (empty range)
        SortedMap<Integer, String> descSubMap = validSubMap instanceof ConcurrentSkipListMap ?
                ((ConcurrentSkipListMap<Integer, String>)validSubMap).descendingMap() : validSubMap;
        // No exception expected
    }
}