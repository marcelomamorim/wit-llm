/*
 * @test
 * @summary Witup-derived checks for HashMap.forEach null action and concurrent modification behavior
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllEntries();
        testConcurrentModificationDetected();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        HashMap<String, Integer> map = new HashMap<>();
        final int[] calls = new int[1];
        map.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testVisitsAllEntries() {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put("k" + i, i);
        }
        map.put(null, null);
        final int[] count = new int[1];
        final long[] sum = new long[1];
        final int[] nullSeen = new int[1];
        map.forEach((k, v) -> {
            count[0]++;
            if (v == null) {
                nullSeen[0]++;
            } else {
                sum[0] += v;
            }
        });
        check(count[0] == 101, "expected 101 visits but got " + count[0]);
        check(sum[0] == 4950L, "expected sum 4950 but got " + sum[0]);
        check(nullSeen[0] == 1, "expected one null value visit");
    }

    static void testConcurrentModificationDetected() {
        final HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        try {
            map.forEach((k, v) -> {
                if (!map.containsKey("extra")) {
                    map.put("extra", 2);
                }
            });
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException after structural modification");
    }
}
