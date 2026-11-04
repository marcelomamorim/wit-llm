/*
 * @test
 * @summary WITUP-derived behavioral tests for ConcurrentSkipListMap.tailMap(K, boolean)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");

        boolean npeThrown = false;
        try {
            map.tailMap(null, true);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        check(npeThrown, "tailMap(null, true) must throw NullPointerException");

        SortedMap<Integer, String> inclusive = map.tailMap(2, true);
        check(inclusive.size() == 2, "inclusive tailMap(2) size expected 2 but was " + inclusive.size());
        check(inclusive.firstKey().equals(2), "inclusive tailMap(2) firstKey expected 2");
        check(inclusive.containsKey(3), "inclusive tailMap(2) must contain key 3");

        SortedMap<Integer, String> exclusive = map.tailMap(2, false);
        check(exclusive.size() == 1, "exclusive tailMap(2) size expected 1 but was " + exclusive.size());
        check(exclusive.firstKey().equals(3), "exclusive tailMap(2) firstKey expected 3");
        check(!exclusive.containsKey(2), "exclusive tailMap(2) must not contain key 2");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
