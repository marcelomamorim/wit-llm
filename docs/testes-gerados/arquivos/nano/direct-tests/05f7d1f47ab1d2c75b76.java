/*
 * @test
 * @summary Test removeIf method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingValues();
        testRemoveIfNoMatch();
        testRemoveIfAllMatch();
    }

    static void testRemoveIfRemovesMatchingValues() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "apple");
        map.put(2, "banana");
        map.put(3, "cherry");
        Predicate<String> predicate = s -> s.startsWith("b");
        boolean result = map.removeIf(value -> predicate.test(value));
        assert result : "Expected removeIf to return true";
        assert map.size() == 2 : "Expected size 2 after removal";
        assert !map.containsValue("banana") : "banana should be removed";
        assert map.containsValue("apple") : "apple should remain";
        assert map.containsValue("cherry") : "cherry should remain";
    }

    static void testRemoveIfNoMatch() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "dog");
        map.put(2, "cat");
        Predicate<String> predicate = s -> s.startsWith("z");
        boolean result = map.removeIf(value -> predicate.test(value));
        assert !result : "Expected removeIf to return false when no elements match";
        assert map.size() == 2 : "Size should remain 2";
        assert map.containsValue("dog") : "dog should remain";
        assert map.containsValue("cat") : "cat should remain";
    }

    static void testRemoveIfAllMatch() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "red");
        map.put(2, "red");
        Predicate<String> predicate = s -> s.equals("red");
        boolean result = map.removeIf(value -> predicate.test(value));
        assert result : "Expected removeIf to return true when all elements match";
        assert map.isEmpty() : "Map should be empty after removing all";
    }
}
