/*
 * @test
 * @summary Behavioral tests for IdentityHashMap.forEach(BiConsumer): identity-based iteration, null key/value handling, NPE on null action, and ConcurrentModificationException on structural modification
 * @run main IdentityHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.List;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapDoesNotInvokeAction();
        testNullKeyAndNullValueAreReported();
        testIdentitySemanticsDuringIteration();
        testAllMappingsVisited();
        testNullActionThrowsNullPointerException();
        testConcurrentModificationDetected();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMapDoesNotInvokeAction() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        final int[] calls = {0};
        m.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked for empty map");
    }

    static void testNullKeyAndNullValueAreReported() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put(null, null);
        final int[] calls = {0};
        final Object[] seen = new Object[2];
        m.forEach((k, v) -> {
            calls[0]++;
            seen[0] = k;
            seen[1] = v;
        });
        check(calls[0] == 1, "expected exactly one invocation, got " + calls[0]);
        check(seen[0] == null, "null key must be passed as null");
        check(seen[1] == null, "null value must be passed as null");
    }

    static void testIdentitySemanticsDuringIteration() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        final String a = new String("k");
        final String b = new String("k");
        m.put(a, "1");
        m.put(b, "2");
        final boolean[] sawA = {false};
        final boolean[] sawB = {false};
        final int[] calls = {0};
        m.forEach((k, v) -> {
            calls[0]++;
            if (k == a) {
                sawA[0] = true;
                check("1".equals(v), "value for key a must be 1");
            } else if (k == b) {
                sawB[0] = true;
                check("2".equals(v), "value for key b must be 2");
            } else {
                throw new RuntimeException("unexpected key passed to action");
            }
        });
        check(calls[0] == 2, "expected 2 invocations, got " + calls[0]);
        check(sawA[0] && sawB[0], "both identity-distinct keys must be visited");
    }

    static void testAllMappingsVisited() {
        IdentityHashMap<Object, Integer> m = new IdentityHashMap<>();
        final List<Object> keys = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Object key = new Object();
            keys.add(key);
            m.put(key, i);
        }
        final List<Object> visited = new ArrayList<>();
        final int[] sum = {0};
        m.forEach((k, v) -> {
            visited.add(k);
            sum[0] += v;
        });
        check(visited.size() == m.size(), "visited count must equal size");
        for (Object key : keys) {
            boolean found = false;
            for (Object seen : visited) {
                if (seen == key) {
                    found = true;
                    break;
                }
            }
            check(found, "every key must be visited by identity");
        }
        check(sum[0] == 49 * 50 / 2, "sum of values must match 0..49");
    }

    static void testNullActionThrowsNullPointerException() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put("x", "y");
        try {
            m.forEach(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConcurrentModificationDetected() {
        final IdentityHashMap<Object, Object> m = new IdentityHashMap<>();
        m.put(new Object(), "v");
        try {
            m.forEach((k, v) -> m.put(new Object(), "w"));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
