/*
 * @test
 * @summary Verifies ConcurrentHashMap entry spliterator forEachRemaining visits remaining entries and rejects null actions
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingVisitsAllEntries();
        testForEachRemainingAfterPartialAdvance();
        testForEachRemainingEmptyMap();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentHashMap<String, Integer> sampleMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        return map;
    }

    static void testForEachRemainingVisitsAllEntries() {
        ConcurrentHashMap<String, Integer> map = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(e -> seen.add(e.getKey() + "=" + e.getValue()));
        Collections.sort(seen);
        List<String> expected = new ArrayList<>();
        expected.add("a=1");
        expected.add("b=2");
        expected.add("c=3");
        check(seen.equals(expected), "forEachRemaining did not visit all entries: " + seen);
    }

    static void testForEachRemainingAfterPartialAdvance() {
        ConcurrentHashMap<String, Integer> map = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        List<String> seen = new ArrayList<>();
        boolean advanced = sp.tryAdvance(e -> seen.add(e.getKey() + "=" + e.getValue()));
        check(advanced, "tryAdvance should consume the first entry");
        check(seen.size() == 1, "tryAdvance should accept exactly one entry");
        sp.forEachRemaining(e -> seen.add(e.getKey() + "=" + e.getValue()));
        check(seen.size() == 3, "expected 3 entries total but got " + seen.size());
        Collections.sort(seen);
        List<String> expected = new ArrayList<>();
        expected.add("a=1");
        expected.add("b=2");
        expected.add("c=3");
        check(seen.equals(expected), "unexpected entries after partial advance: " + seen);
    }

    static void testForEachRemainingEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        int[] count = new int[1];
        sp.forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "action should not be invoked for an empty map");
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        try {
            sp.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
