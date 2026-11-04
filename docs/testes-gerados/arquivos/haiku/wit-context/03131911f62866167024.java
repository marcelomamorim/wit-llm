/*
 * @test
 * @summary Behavioral checks for HashMap key spliterator tryAdvance (null action, empty map, traversal, concurrent modification)
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testTraversalReturnsAllKeysThenFalse();
        testConcurrentModificationDetected();
    }

    static void testNullActionThrowsNPE() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Spliterator<String> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testEmptyMapReturnsFalse() {
        Map<String, Integer> map = new HashMap<>();
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> action = seen::add;
        if (sp.tryAdvance(action)) {
            throw new RuntimeException("tryAdvance on empty map should return false");
        }
        if (!seen.isEmpty()) {
            throw new RuntimeException("Action must not be invoked for empty map");
        }
    }

    static void testTraversalReturnsAllKeysThenFalse() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> action = seen::add;
        int calls = 0;
        while (sp.tryAdvance(action)) {
            calls++;
            if (calls > 3) {
                throw new RuntimeException("tryAdvance returned true more times than entries");
            }
        }
        if (calls != 3 || seen.size() != 3) {
            throw new RuntimeException("Expected 3 keys, got " + seen.size());
        }
        if (!seen.containsAll(map.keySet())) {
            throw new RuntimeException("Traversed keys do not match map keys");
        }
        if (sp.tryAdvance(action)) {
            throw new RuntimeException("tryAdvance after exhaustion should return false");
        }
        if (seen.size() != 3) {
            throw new RuntimeException("Action invoked after exhaustion");
        }
    }

    static void testConcurrentModificationDetected() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Set<String> keys = map.keySet();
        Spliterator<String> sp = keys.spliterator();
        Consumer<String> action = k -> map.put("b", 2);
        boolean thrown = false;
        try {
            sp.tryAdvance(action);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException after structural modification");
        }
    }
}
