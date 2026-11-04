/*
 * @test
 * @summary Test removeIf method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingValues();
        testRemoveIfNoMatch();
        testRemoveIfNullPredicate();
    }

    static void testRemoveIfRemovesMatchingValues() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "apple");
        map.put(2, "banana");
        map.put(3, "cherry");
        map.put(4, "date");
        Predicate<String> predicate = s -> s.startsWith("b") || s.startsWith("c");
        boolean result = map.removeIf(value -> predicate.test(value));
        assert result : "Expected removeIf to return true";
        assert map.size() == 2 : "Expected size 2 after removal";
        assert map.containsKey(1) : "Map should contain key 1";
        assert map.containsKey(4) : "Map should contain key 4";
        assert !map.containsKey(2) : "Map should not contain key 2";
        assert !map.containsKey(3) : "Map should not contain key 3";
    }

    static void testRemoveIfNoMatch() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "elephant");
        map.put(20, "fox");
        Predicate<String> predicate = s -> s.startsWith("z");
        boolean result = map.removeIf(value -> predicate.test(value));
        assert !result : "Expected removeIf to return false when no elements match";
        assert map.size() == 2 : "Map size should remain 2";
        assert map.containsKey(10) : "Map should contain key 10";
        assert map.containsKey(20) : "Map should contain key 20";
    }

    static void testRemoveIfNullPredicate() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "grape");
        try {
            map.removeIf(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        assert map.size() == 1 : "Map should remain unchanged";
        assert map.containsKey(5) : "Map should contain key 5";
    }
}
