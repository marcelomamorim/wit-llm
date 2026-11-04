/*
 * @test
 * @summary Test for removeIf method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
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

    private static void testRemoveIfWithValidPredicate() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Predicate<String> predicate = s -> s.length() == 3;
        boolean result = map.removeIf(predicate);
        if (!result) {
            throw new AssertionError("Expected removeIf to return true when elements are removed");
        }
        if (map.containsKey(1) || map.containsKey(2) || map.containsKey(3)) {
            throw new AssertionError("Map should not contain keys after removal");
        }
        if (map.size() != 0) {
            throw new AssertionError("Map size should be zero after removal");
        }
    }
}
