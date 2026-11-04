/*
 * @test
 * @summary Behavioral tests for HashMap.forEach-style key iteration via keySet().forEach(Consumer)
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllKeys();
        testSingleKeyVisitedOnce();
        testModificationDuringIterationThrowsCME();
        System.out.println("All HashMapWitupTest tests passed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Set<String> keys = map.keySet();
        boolean thrown = false;
        try {
            keys.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        HashMap<String, Integer> map = new HashMap<>();
        final int[] calls = {0};
        map.keySet().forEach(k -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testVisitsAllKeys() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, "v" + i);
        }
        final int[] count = {0};
        final long[] sum = {0L};
        map.keySet().forEach(k -> {
            count[0]++;
            sum[0] += k;
        });
        check(count[0] == 100, "expected 100 visits, got " + count[0]);
        check(sum[0] == 4950L, "expected key sum 4950, got " + sum[0]);
    }

    static void testSingleKeyVisitedOnce() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("only", 42);
        final String[] seen = {null};
        final int[] count = {0};
        map.keySet().forEach(k -> {
            seen[0] = k;
            count[0]++;
        });
        check(count[0] == 1, "single key must be visited exactly once");
        check("only".equals(seen[0]), "visited key must be 'only'");
    }

    static void testModificationDuringIterationThrowsCME() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        final boolean[] added = {false};
        boolean thrown = false;
        try {
            map.keySet().forEach(k -> {
                if (!added[0]) {
                    added[0] = true;
                    map.put("structural-change", 2);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEach must throw ConcurrentModificationException");
    }
}
