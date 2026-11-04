/*
 * @test
 * @summary Test removeIf method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithMatchingEntries();
        testRemoveIfWithNoMatchingEntries();
        testRemoveIfWithNullPredicate();
    }

    static void testRemoveIfWithMatchingEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Predicate<Map.Entry<Integer, String>> predicate = e -> e.getKey() % 2 == 1;
        boolean result = map.removeIf(predicate);
        assert result : "Expected removeIf to return true when entries are removed";
        assert map.size() == 1 : "Expected size 1 after removal";
        assert map.containsKey(2) : "Expected key 2 to remain";
        assert !map.containsKey(1) : "Expected key 1 to be removed";
        assert !map.containsKey(3) : "Expected key 3 to be removed";
    }

    static void testRemoveIfWithNoMatchingEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(4, "four");
        map.put(6, "six");
        Predicate<Map.Entry<Integer, String>> predicate = e -> e.getKey() < 0;
        boolean result = map.removeIf(predicate);
        assert !result : "Expected removeIf to return false when no entries are removed";
        assert map.size() == 2 : "Expected size 2 when no entries are removed";
        assert map.containsKey(4) : "Expected key 4 to remain";
        assert map.containsKey(6) : "Expected key 6 to remain";
    }

    static void testRemoveIfWithNullPredicate() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            map.removeIf(null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
