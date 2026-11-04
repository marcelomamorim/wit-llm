/*
 * @test
 * @summary Tests IdentityHashMap.replaceAll identity semantics, null handling, null function and concurrent modification
 * @run main IdentityHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNpe();
        testUpdatesValuesByIdentity();
        testNullKeyAndNullValue();
        testConcurrentModificationDetected();
    }

    static void testNullFunctionThrowsNpe() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put("a", "b");
        try {
            m.replaceAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testUpdatesValuesByIdentity() {
        String k1 = new String("k");
        String k2 = new String("k");
        IdentityHashMap<String, Integer> m = new IdentityHashMap<>();
        m.put(k1, 1);
        m.put(k2, 2);
        m.replaceAll((k, v) -> v * 10);
        if (m.size() != 2) {
            throw new RuntimeException("unexpected size: " + m.size());
        }
        if (m.get(k1) != 10) {
            throw new RuntimeException("unexpected value for k1: " + m.get(k1));
        }
        if (m.get(k2) != 20) {
            throw new RuntimeException("unexpected value for k2: " + m.get(k2));
        }
    }

    static void testNullKeyAndNullValue() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put(null, null);
        m.put("x", "keep");
        m.replaceAll((k, v) -> (k == null && v == null) ? "nullpair" : v);
        if (!"nullpair".equals(m.get(null))) {
            throw new RuntimeException("null key not handled: " + m.get(null));
        }
        if (!"keep".equals(m.get("x"))) {
            throw new RuntimeException("non-null entry changed unexpectedly");
        }
    }

    static void testConcurrentModificationDetected() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put("a", "1");
        m.put("b", "2");
        try {
            m.replaceAll((k, v) -> {
                m.put(new String("new"), "x");
                return v;
            });
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
