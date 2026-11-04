/*
 * @test
 * @summary Behavioral tests for WeakHashMap.forEach(BiConsumer): null action, empty map, all entries visited, null key and null value handling, and ConcurrentModificationException on structural modification.
 * @run main WeakHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapNoInvocation();
        testVisitsAllEntries();
        testNullKeyAndNullValueVisited();
        testStructuralModificationThrowsCME();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        try {
            map.forEach((BiConsumer<String, String>) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyMapNoInvocation() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        int[] calls = {0};
        map.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testVisitsAllEntries() {
        String k1 = "k1";
        String k2 = "k2";
        String k3 = "k3";
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(k1, "v1");
        map.put(k2, "v2");
        map.put(k3, "v3");

        Map<String, String> seen = new HashMap<>();
        int[] calls = {0};
        map.forEach((k, v) -> {
            calls[0]++;
            seen.put(k, v);
        });

        check(calls[0] == 3, "expected 3 invocations, got " + calls[0]);
        check(seen.size() == 3, "expected 3 distinct keys seen");
        check("v1".equals(seen.get(k1)), "k1 value mismatch");
        check("v2".equals(seen.get(k2)), "k2 value mismatch");
        check("v3".equals(seen.get(k3)), "k3 value mismatch");
    }

    static void testNullKeyAndNullValueVisited() {
        String key = "nonNull";
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(null, "nullKeyValue");
        map.put(key, null);

        Map<String, String> seen = new HashMap<>();
        map.forEach((k, v) -> seen.put(k, v));

        check(seen.size() == 2, "expected 2 entries, got " + seen.size());
        check(seen.containsKey(null), "null key must be passed to action");
        check("nullKeyValue".equals(seen.get(null)), "null key value mismatch");
        check(seen.containsKey(key), "non-null key missing");
        check(seen.get(key) == null, "null value must be passed as null");
    }

    static void testStructuralModificationThrowsCME() {
        String a = "a";
        String b = "b";
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(a, "1");
        map.put(b, "2");
        try {
            map.forEach((k, v) -> map.put("added", "x"));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
