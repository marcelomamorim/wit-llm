/*
 * @test
 * @summary Behavioral tests for LinkedHashMap.forEach(Consumer): insertion order, null action, empty map, and concurrent modification detection
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testInsertionOrder();
        testNullActionThrowsNPE();
        testEmptyMapInvokesNoAction();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("b", 1);
        map.put("a", 2);
        map.put("c", 3);
        List<String> keys = new ArrayList<>();
        Consumer<String> collector = keys::add;
        map.forEach(collector);
        check(keys.toString().equals("[b, a, c]"),
              "Unexpected iteration order: " + keys);
    }

    static void testNullActionThrowsNPE() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        Consumer<String> nullAction = null;
        try {
            map.forEach(nullAction);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyMapInvokesNoAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int[] calls = new int[1];
        Consumer<String> counter = k -> calls[0]++;
        map.forEach(counter);
        check(calls[0] == 0, "Action invoked on empty map");
    }

    static void testConcurrentModificationDetected() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        Consumer<String> inserter = k -> {
            if (k.equals("a")) {
                map.put("x", 2);
            }
        };
        try {
            map.forEach(inserter);
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
