/*
 * @test
 * @summary Test ConcurrentSkipListMap.clear() method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test clear on empty map
        map.clear();
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after clear on empty map");
        }

        // Test clear on map with one element
        map.put(1, "one");
        if (map.isEmpty()) {
            throw new AssertionError("Map should not be empty after put");
        }
        map.clear();
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after clear");
        }

        // Test clear on map with multiple elements
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        if (map.size() != 3) {
            throw new AssertionError("Map size should be 3 after puts");
        }
        map.clear();
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after clear");
        }

        // Test clear on map with null values (ConcurrentSkipListMap does not allow null values, so skip)

        // Test clear on map with natural ordering
        ConcurrentSkipListMap<String, Integer> map2 = new ConcurrentSkipListMap<>();
        map2.put("a", 1);
        map2.put("b", 2);
        map2.clear();
        if (!map2.isEmpty()) {
            throw new AssertionError("Map2 should be empty after clear");
        }

        // Test clear on map with custom comparator
        ConcurrentSkipListMap<String, Integer> map3 = new ConcurrentSkipListMap<>(
            (s1, s2) -> s2.compareTo(s1)); // reverse order
        map3.put("a", 1);
        map3.put("b", 2);
        map3.clear();
        if (!map3.isEmpty()) {
            throw new AssertionError("Map3 should be empty after clear");
        }

        System.out.println("All tests passed.");
    }
}
