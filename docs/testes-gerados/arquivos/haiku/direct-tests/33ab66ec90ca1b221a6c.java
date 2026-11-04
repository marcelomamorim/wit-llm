/*
 * @test
 * @summary Verify HashMap.forEach: null action, empty map, all entries visited, null key/value, and ConcurrentModificationException detection
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllEntries();
        testNullKeyAndNullValueAreVisited();
        testStructuralModificationDuringForEachThrowsCme();
        testExistingKeyUpdateDoesNotThrow();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        boolean thrown = false;
        try {
            m.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on non-empty map must throw NullPointerException");

        HashMap<String, Integer> empty = new HashMap<>();
        thrown = false;
        try {
            empty.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on empty map must throw NullPointerException");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        HashMap<String, Integer> m = new HashMap<>();
        int[] calls = new int[1];
        m.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked for an empty map");
    }

    static void testVisitsAllEntries() {
        HashMap<String, Integer> m = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            m.put("k" + i, i);
        }
        int[] count = new int[1];
        long[] sum = new long[1];
        boolean[] mismatch = new boolean[1];
        m.forEach((k, v) -> {
            count[0]++;
            sum[0] += v;
            if (!v.equals(m.get(k))) {
                mismatch[0] = true;
            }
        });
        check(count[0] == 100, "expected 100 visits but got " + count[0]);
        check(sum[0] == 4950L, "expected value sum 4950 but got " + sum[0]);
        check(!mismatch[0], "visited (key,value) pair does not match map contents");
    }

    static void testNullKeyAndNullValueAreVisited() {
        HashMap<String, String> m = new HashMap<>();
        m.put(null, null);
        m.put("x", null);
        int[] count = new int[1];
        boolean[] sawNullKey = new boolean[1];
        boolean[] sawNullValueForX = new boolean[1];
        m.forEach((k, v) -> {
            count[0]++;
            if (k == null && v == null) {
                sawNullKey[0] = true;
            }
            if ("x".equals(k) && v == null) {
                sawNullValueForX[0] = true;
            }
        });
        check(count[0] == 2, "expected 2 visits but got " + count[0]);
        check(sawNullKey[0], "null key with null value must be passed to action");
        check(sawNullValueForX[0], "null value must be passed to action");
    }

    static void testStructuralModificationDuringForEachThrowsCme() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        boolean[] added = new boolean[1];
        boolean thrown = false;
        try {
            m.forEach((k, v) -> {
                if (!added[0]) {
                    added[0] = true;
                    m.put("newKey", 3);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(added[0], "action must have been invoked at least once");
        check(thrown, "adding a new key during forEach must throw ConcurrentModificationException");
    }

    static void testExistingKeyUpdateDoesNotThrow() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        boolean[] updated = new boolean[1];
        m.forEach((k, v) -> {
            if (!updated[0]) {
                updated[0] = true;
                m.put("a", 100);
            }
        });
        check(updated[0], "action must have been invoked");
        check(m.get("a") == 100, "value update during forEach must be visible");
        check(m.size() == 2, "size must remain 2 after updating existing key");
    }
}
