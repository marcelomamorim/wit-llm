/*
 * @test
 * @summary WITUP-derived checks for LinkedHashMap.forEach: null action, insertion-order traversal, empty map and concurrent structural modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testVisitsValuesInInsertionOrder();
        testEmptyMapDoesNotInvokeAction();
        testStructuralModificationDuringForEachIsDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testVisitsValuesInInsertionOrder() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("b", 2);
        map.put("a", 1);
        map.put("c", 3);
        map.put("a", 5);
        final List<Integer> seen = new ArrayList<>();
        map.forEach(v -> seen.add(v));
        check(seen.size() == 3, "expected 3 visited values but was " + seen);
        check(seen.get(0) == 2, "first value must be 2 but was " + seen);
        check(seen.get(1) == 5, "second value must be 5 but was " + seen);
        check(seen.get(2) == 3, "third value must be 3 but was " + seen);
    }

    static void testEmptyMapDoesNotInvokeAction() {
        Map<String, Integer> map = new LinkedHashMap<>();
        final int[] calls = {0};
        map.forEach(v -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testStructuralModificationDuringForEachIsDetected() {
        final Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean thrown = false;
        try {
            map.forEach(v -> {
                if (!map.containsKey("z")) {
                    map.put("z", 9);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEach must throw ConcurrentModificationException");
    }
}
