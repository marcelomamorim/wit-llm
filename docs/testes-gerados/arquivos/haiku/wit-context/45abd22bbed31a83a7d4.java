/*
 * @test
 * @summary Behavioral tests for HashMap.forEach: null action, empty map, full traversal and concurrent modification detection
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllEntriesExactlyOnce();
        testConcurrentModificationIsDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        HashMap<String, Integer> map = new HashMap<>();
        final int[] calls = {0};
        Consumer<Map.Entry<String, Integer>> action = e -> calls[0]++;
        map.forEach(action);
        check(calls[0] == 0, "action must not be invoked for an empty map");
    }

    static void testVisitsAllEntriesExactlyOnce() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i * 2);
        }
        final int[] count = {0};
        final long[] sum = {0L};
        final boolean[] valuesOk = {true};
        map.forEach(e -> {
            count[0]++;
            sum[0] += e.getKey();
            if (e.getValue() != e.getKey() * 2) {
                valuesOk[0] = false;
            }
        });
        check(count[0] == 100, "expected 100 visits but was " + count[0]);
        check(sum[0] == 4950L, "sum of keys mismatch: " + sum[0]);
        check(valuesOk[0], "entry key/value pairs were not preserved during traversal");
    }

    static void testConcurrentModificationIsDetected() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final boolean[] done = {false};
        boolean thrown = false;
        try {
            map.forEach(e -> {
                if (!done[0]) {
                    done[0] = true;
                    map.put("zz", 3);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEach must throw ConcurrentModificationException");
    }
}
