/*
 * @test
 * @summary Behavioral tests for LinkedHashMap.forEach: null action, insertion order, access order, and concurrent modification detection
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testIterationFollowsInsertionOrder();
        testEmptyMapInvokesNoAction();
        testConcurrentModificationDetected();
        testAccessOrderIteration();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testIterationFollowsInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("z", 1);
        map.put("a", 2);
        map.put("m", 3);
        map.put("z", 4);
        final List<String> keys = new ArrayList<>();
        final List<Integer> values = new ArrayList<>();
        map.forEach(e -> {
            keys.add(e.getKey());
            values.add(e.getValue());
        });
        check(keys.toString().equals("[z, a, m]"), "unexpected key order: " + keys);
        check(values.toString().equals("[4, 2, 3]"), "unexpected value order: " + values);
    }

    static void testEmptyMapInvokesNoAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        final int[] calls = new int[1];
        map.forEach(e -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testConcurrentModificationDetected() {
        final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean thrown = false;
        try {
            map.forEach(e -> {
                if (!map.containsKey("x")) {
                    map.put("x", 0);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEach must throw ConcurrentModificationException");
    }

    static void testAccessOrderIteration() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.get("a");
        final List<String> keys = new ArrayList<>();
        map.forEach(e -> keys.add(e.getKey()));
        check(keys.toString().equals("[b, c, a]"), "unexpected access-order iteration: " + keys);
    }
}
