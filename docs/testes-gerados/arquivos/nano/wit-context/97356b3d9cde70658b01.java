/*
 * @test
 * @summary Test removeIf method of ConcurrentSkipListMap for null predicate
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    static void testRemoveIfWithNullPredicate() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean exceptionThrown = false;
        try {
            map.removeIf(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null predicate");
        }
    }

    static void testRemoveIfWithValidPredicate() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Predicate<Map.Entry<Integer, String>> predicate = e -> e.getKey() % 2 == 1;
        boolean result = map.removeIf(predicate);
        if (!result) {
            throw new AssertionError("removeIf should return true when entries are removed");
        }
        if (map.containsKey(1) || map.containsKey(3)) {
            throw new AssertionError("Entries with odd keys should be removed");
        }
        if (!map.containsKey(2)) {
            throw new AssertionError("Entry with key 2 should remain");
        }
    }
}
