/*
 * @test
 * @summary Verifies ConcurrentHashMap.keySet(V) rejects null mapped values and returns a view that uses the given mapped value for additions
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullMappedValueThrowsNPE();
        testReturnedViewExposesMappedValue();
        testAddUsesMappedValue();
        testViewReflectsBackingMap();
    }

    static void testNullMappedValueThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            map.keySet((Integer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("keySet(null) should throw NullPointerException");
        }
    }

    static void testReturnedViewExposesMappedValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        KeySetView<String, Integer> view = map.keySet(7);
        if (view == null) {
            throw new AssertionError("keySet(non-null) must not return null");
        }
        if (!Integer.valueOf(7).equals(view.getMappedValue())) {
            throw new AssertionError("getMappedValue() mismatch: " + view.getMappedValue());
        }
    }

    static void testAddUsesMappedValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        KeySetView<String, Integer> view = map.keySet(42);
        if (!view.add("a")) {
            throw new AssertionError("add of new key should return true");
        }
        if (!Integer.valueOf(42).equals(map.get("a"))) {
            throw new AssertionError("added key should map to mapped value 42, was " + map.get("a"));
        }
        if (!view.contains("a")) {
            throw new AssertionError("view should contain added key");
        }
        if (view.add("a")) {
            throw new AssertionError("add of existing key should return false");
        }
        if (map.get("a") == null || map.get("a") != 42) {
            throw new AssertionError("existing mapping must not change");
        }
    }

    static void testViewReflectsBackingMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 1);
        KeySetView<String, Integer> view = map.keySet(0);
        if (!view.contains("x")) {
            throw new AssertionError("view should expose keys of backing map");
        }
        if (view.size() != 1) {
            throw new AssertionError("view size should be 1, was " + view.size());
        }
        map.put("y", 2);
        if (view.size() != 2 || !view.contains("y")) {
            throw new AssertionError("view should reflect later insertions into backing map");
        }
    }
}
