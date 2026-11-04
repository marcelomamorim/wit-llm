/*
 * @test
 * @summary WeakHashMap.forEach: null action NPE, empty map, visits entries, null key/value, CME on concurrent modification
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapNoCallbacks();
        testVisitsAllEntries();
        testNullKeyAndNullValueVisited();
        testConcurrentModificationDetected();
        System.out.println("PASS");
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
            map.forEach(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyMapNoCallbacks() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        final int[] calls = {0};
        map.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action invoked for empty map: " + calls[0]);
    }

    static void testVisitsAllEntries() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        final List<String> seen = new ArrayList<>();
        map.forEach((k, v) -> seen.add(k + "=" + v));
        check(seen.size() == 3, "expected 3 visits but got " + seen.size());
        check(seen.contains("k1=v1"), "missing k1=v1");
        check(seen.contains("k2=v2"), "missing k2=v2");
        check(seen.contains("k3=v3"), "missing k3=v3");
    }

    static void testNullKeyAndNullValueVisited() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(null, "nullKeyValue");
        map.put("n", null);
        final List<String> seen = new ArrayList<>();
        map.forEach((k, v) -> seen.add(k + "=" + v));
        check(seen.size() == 2, "expected 2 visits but got " + seen.size());
        check(seen.contains("null=nullKeyValue"), "null key not visited correctly");
        check(seen.contains("n=null"), "null value not visited correctly");
    }

    static void testConcurrentModificationDetected() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        try {
            map.forEach((k, v) -> map.put("d", "new"));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
