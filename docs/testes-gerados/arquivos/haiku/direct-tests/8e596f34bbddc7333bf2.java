/*
 * @test
 * @summary LinkedHashMap.forEach iterates in insertion order, rejects null action, and detects structural modification
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
        testEmptyMapDoesNotInvokeAction();
        testNullActionThrowsNPE();
        testStructuralModificationThrowsCME();
        testExistingKeyPutDuringIterationIsAllowed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("b", 1);
        map.put("a", 2);
        map.put("c", 3);
        List<Integer> vals = new ArrayList<>();
        map.forEach(v -> vals.add(v));
        check(vals.size() == 3, "expected 3 values, got " + vals.size());
        check(vals.get(0) == 1 && vals.get(1) == 2 && vals.get(2) == 3,
              "unexpected order: " + vals);
    }

    static void testEmptyMapDoesNotInvokeAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int[] calls = new int[1];
        map.forEach(v -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty map");
    }

    static void testNullActionThrowsNPE() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        Consumer<Integer> nullAction = null;
        boolean thrown = false;
        try {
            map.forEach(nullAction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testStructuralModificationThrowsCME() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean[] added = new boolean[1];
        boolean thrown = false;
        try {
            map.forEach(v -> {
                if (!added[0]) {
                    map.put("z", 0);
                    added[0] = true;
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural change");
    }

    static void testExistingKeyPutDuringIterationIsAllowed() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.forEach(v -> {
            if (v == 1) {
                map.put("a", 5);
            }
        });
        check(map.get("a") == 5, "value of existing key was not updated");
    }
}
