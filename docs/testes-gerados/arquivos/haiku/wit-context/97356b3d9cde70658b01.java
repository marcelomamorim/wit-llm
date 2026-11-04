/*
 * @test
 * @summary Tests ConcurrentSkipListMap entrySet removeIf behavior, including null filter handling and submap views
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemovesMatchingEntries();
        testNoMatchReturnsFalse();
        testSubMapRemovesOnlyWithinRange();
        System.out.println("All tests passed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentSkipListMap<Integer, Integer> newMapWithKeys1To5() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(i, i * 10);
        }
        return map;
    }

    static void testNullFilterThrowsNPE() {
        ConcurrentSkipListMap<Integer, Integer> map = newMapWithKeys1To5();
        try {
            map.entrySet().removeIf(null);
        } catch (NullPointerException expected) {
            check(map.size() == 5, "map must not be modified when filter is null");
            return;
        }
        throw new RuntimeException("NullPointerException expected for null filter");
    }

    static void testRemovesMatchingEntries() {
        ConcurrentSkipListMap<Integer, Integer> map = newMapWithKeys1To5();
        boolean removed = map.entrySet().removeIf(e -> e.getKey() % 2 == 0);
        check(removed, "removeIf should return true when entries are removed");
        check(map.keySet().toString().equals("[1, 3, 5]"),
              "unexpected keys after removal: " + map.keySet());
        check(map.get(2) == null && map.get(4) == null, "even keys must be absent");
        check(map.get(3) == 30, "odd key values must be preserved");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentSkipListMap<Integer, Integer> map = newMapWithKeys1To5();
        boolean removed = map.entrySet().removeIf(e -> e.getKey() > 100);
        check(!removed, "removeIf should return false when no entry matches");
        check(map.size() == 5, "size must remain 5");
        check(map.keySet().toString().equals("[1, 2, 3, 4, 5]"),
              "keys must be unchanged: " + map.keySet());
    }

    static void testSubMapRemovesOnlyWithinRange() {
        ConcurrentSkipListMap<Integer, Integer> map = newMapWithKeys1To5();
        ConcurrentNavigableMap<Integer, Integer> head = map.headMap(3);

        boolean noneInRange = head.entrySet().removeIf(e -> e.getKey() == 4);
        check(!noneInRange, "key 4 is outside headMap(3) and must not be removed through the view");
        check(map.size() == 5, "backing map must be unchanged after non-matching submap removal");

        boolean removed = head.entrySet().removeIf(e -> e.getKey() == 1);
        check(removed, "removeIf on submap should remove key 1");
        check(map.keySet().toString().equals("[2, 3, 4, 5]"),
              "unexpected backing map keys: " + map.keySet());
    }
}
