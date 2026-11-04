/*
 * @test
 * @summary Tests LinkedHashMap.forEach: insertion-order iteration, NullPointerException for null action, and ConcurrentModificationException on structural modification during iteration
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testInsertionOrder();
        testReinsertKeepsOrder();
        testNullAction();
        testEmptyMapNoCalls();
        testConcurrentModificationOnPut();
        testConcurrentModificationOnRemove();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("b", 2);
        map.put("a", 1);
        map.put("c", 3);
        final List<String> out = new ArrayList<String>();
        map.forEach((k, v) -> out.add(k + "=" + v));
        List<String> expected = new ArrayList<String>();
        expected.add("b=2");
        expected.add("a=1");
        expected.add("c=3");
        check(out.equals(expected), "unexpected order: " + out);
    }

    static void testReinsertKeepsOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("x", 1);
        map.put("y", 2);
        map.put("x", 9);
        final List<String> out = new ArrayList<String>();
        map.forEach((k, v) -> out.add(k + "=" + v));
        check(out.size() == 2, "wrong size: " + out);
        check(out.get(0).equals("x=9"), "reinsert changed order or value: " + out);
        check(out.get(1).equals("y=2"), "unexpected second entry: " + out);
    }

    static void testNullAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testEmptyMapNoCalls() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        final int[] calls = new int[1];
        map.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty map");
    }

    static void testConcurrentModificationOnPut() {
        final LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("a", 1);
        final boolean[] added = new boolean[1];
        boolean thrown = false;
        try {
            map.forEach((k, v) -> {
                if (!added[0]) {
                    added[0] = true;
                    map.put("x", 0);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural put");
    }

    static void testConcurrentModificationOnRemove() {
        final LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        boolean thrown = false;
        try {
            map.forEach((k, v) -> {
                if (k.equals("a")) {
                    map.remove("b");
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural remove");
    }
}
