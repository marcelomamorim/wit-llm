/*
 * @test
 * @summary Verify KeySetView.forEach rejects a null action and visits every key exactly once
 * @run main ConcurrentHashMapWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPEOnNonEmptyMap();
        testNullActionThrowsNPEOnEmptyMap();
        testEmptyMapNoInvocation();
        testVisitsAllKeys();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPEOnNonEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.keySet().forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on non-empty map");
    }

    static void testNullActionThrowsNPEOnEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            map.keySet().forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on empty map");
    }

    static void testEmptyMapNoInvocation() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        final int[] calls = {0};
        map.keySet().forEach(k -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked for empty map");
    }

    static void testVisitsAllKeys() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 1);
        map.put("y", 2);
        map.put("z", 3);
        final List<String> seen = new ArrayList<>();
        map.keySet().forEach(k -> seen.add(k));
        check(seen.size() == 3, "expected 3 keys visited but got " + seen.size());
        check(seen.contains("x") && seen.contains("y") && seen.contains("z"),
              "not all keys were visited: " + seen);
    }
}
