/*
 * @test
 * @summary Behavioral checks for ConcurrentSkipListMap.lowerEntry using only public API
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(map.lowerEntry(1) == null, "lowerEntry on empty map must return null");
    }

    static void testNaturalOrderStrictlyLess() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");

        Map.Entry<Integer, String> e = map.lowerEntry(3);
        check(e != null, "lowerEntry(3) must not be null");
        check(e.getKey() == 1, "lowerEntry(3) key expected 1 but was " + e.getKey());
        check("a".equals(e.getValue()), "lowerEntry(3) value expected a");
    }

    static void testAbsentKeyReturnsPredecessor() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");

        Map.Entry<Integer, String> e = map.lowerEntry(4);
        check(e != null && e.getKey() == 3, "lowerEntry(4) must return key 3");

        e = map.lowerEntry(2);
        check(e != null && e.getKey() == 1, "lowerEntry(2) must return key 1");

        e = map.lowerEntry(6);
        check(e != null && e.getKey() == 5, "lowerEntry(6) must return key 5");
    }

    static void testNoLowerEntry() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");

        check(map.lowerEntry(1) == null, "lowerEntry(1) must be null when 1 is smallest");
        check(map.lowerEntry(0) == null, "lowerEntry(0) must be null when below all keys");
    }

    static void testReverseComparator() {
        ConcurrentSkipListMap<Integer, String> map =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");

        // With reverse ordering, "lower" means numerically greater.
        Map.Entry<Integer, String> e = map.lowerEntry(3);
        check(e != null && e.getKey() == 5, "reverse order lowerEntry(3) must return key 5");

        check(map.lowerEntry(5) == null, "reverse order lowerEntry(5) must be null");
    }

    static void testAfterRemoval() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        map.remove(3);

        Map.Entry<Integer, String> e = map.lowerEntry(4);
        check(e != null && e.getKey() == 1, "after removing 3, lowerEntry(4) must return key 1");
    }

    public static void main(String[] args) {
        testEmptyMap();
        testNaturalOrderStrictlyLess();
        testAbsentKeyReturnsPredecessor();
        testNoLowerEntry();
        testReverseComparator();
        testAfterRemoval();
    }
}
