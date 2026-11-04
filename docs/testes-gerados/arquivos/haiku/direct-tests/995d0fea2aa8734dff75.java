/*
 * @test
 * @summary Behavioral tests for LinkedHashMap.replaceAll: null function, insertion-order replacement, empty map, and concurrent modification detection
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNpe();
        testReplacesValuesInInsertionOrder();
        testEmptyMapInvokesNothing();
        testStructuralModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNpe() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        try {
            map.replaceAll(null);
            check(false, "expected NullPointerException for null function");
        } catch (NullPointerException e) {
            // expected
        }
        check(map.get("a") == 1, "map must be unchanged after NPE");
    }

    static void testReplacesValuesInInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        final List<String> seen = new ArrayList<>();
        map.replaceAll((k, v) -> {
            seen.add(k);
            return v * 10;
        });
        check(seen.size() == 3, "function must be called once per entry");
        check(seen.get(0).equals("a"), "first visited key must be a");
        check(seen.get(1).equals("b"), "second visited key must be b");
        check(seen.get(2).equals("c"), "third visited key must be c");
        check(map.get("a") == 10, "value of a must be 10");
        check(map.get("b") == 20, "value of b must be 20");
        check(map.get("c") == 30, "value of c must be 30");
        List<String> keys = new ArrayList<>(map.keySet());
        check(keys.get(0).equals("a") && keys.get(1).equals("b") && keys.get(2).equals("c"),
              "iteration order must be preserved");
    }

    static void testEmptyMapInvokesNothing() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        final boolean[] called = new boolean[1];
        map.replaceAll((k, v) -> {
            called[0] = true;
            return v;
        });
        check(!called[0], "function must not be invoked on empty map");
        check(map.isEmpty(), "empty map must remain empty");
    }

    static void testStructuralModificationDetected() {
        final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean thrown = false;
        try {
            map.replaceAll((k, v) -> {
                if (k.equals("a") && !map.containsKey("new")) {
                    map.put("new", 99);
                }
                return v + 100;
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during replaceAll must throw ConcurrentModificationException");
    }
}
