/*
 * @test
 * @summary Verify ConcurrentHashMap key spliterator tryAdvance null check, empty, single and full traversal behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testSingleKey();
        testAllKeysVisited();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Spliterator<String> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) on non-empty map must throw NullPointerException");
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> c = seen::add;
        check(!sp.tryAdvance(c), "tryAdvance on empty map must return false");
        check(seen.isEmpty(), "action must not be invoked on empty map");
    }

    static void testSingleKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("k", 1);
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> c = seen::add;
        check(sp.tryAdvance(c), "first tryAdvance must return true when a key exists");
        check(seen.size() == 1 && seen.get(0).equals("k"), "action must receive the single key");
        check(!sp.tryAdvance(c), "second tryAdvance must return false after exhaustion");
        check(seen.size() == 1, "action must not be invoked after exhaustion");
    }

    static void testAllKeysVisited() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Set<String> expected = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String key = "k" + i;
            map.put(key, i);
            expected.add(key);
        }
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> c = seen::add;
        while (sp.tryAdvance(c)) {
            // drain
        }
        check(seen.size() == 100, "all 100 keys must be visited exactly once, got " + seen.size());
        Set<String> actual = new HashSet<>(seen);
        check(actual.equals(expected), "visited keys must match the map's keys");
    }
}
