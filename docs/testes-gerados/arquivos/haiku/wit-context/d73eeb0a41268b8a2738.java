/*
 * @test
 * @summary Witup-derived checks for ConcurrentHashMap.forEach: null action NPE and observable traversal behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllEntries();
        testEmptyMapInvokesNoAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testVisitsAllEntries() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        final Map<String, Integer> seen = new HashMap<>();
        BiConsumer<String, Integer> action = (k, v) -> seen.put(k, v);
        map.forEach(action);
        check(seen.size() == 3, "forEach must visit every entry exactly once, got size " + seen.size());
        check(seen.equals(map), "forEach must observe the same key/value pairs as the map");
    }

    static void testEmptyMapInvokesNoAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        final int[] calls = new int[1];
        BiConsumer<String, Integer> action = (k, v) -> calls[0]++;
        map.forEach(action);
        check(calls[0] == 0, "forEach on empty map must not invoke action");
    }
}
