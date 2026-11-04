/*
 * @test
 * @summary Behavioral tests for WeakHashMap.replaceAll covering null function, value updates, null key/value handling, concurrent modification and empty map
 * @run main WeakHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testUpdatesValues();
        testNullKeyAndNullValue();
        testConcurrentModificationDetected();
        testEmptyMapDoesNotInvokeFunction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNPE() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        m.put("a", "1");
        boolean thrown = false;
        try {
            m.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replaceAll(null) must throw NullPointerException");
        check("1".equals(m.get("a")), "map must be unchanged after failed replaceAll");
    }

    static void testUpdatesValues() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        m.put("a", "1");
        m.put("b", "2");
        m.replaceAll((k, v) -> k + v);
        check("a1".equals(m.get("a")), "value for a should be a1 but was " + m.get("a"));
        check("b2".equals(m.get("b")), "value for b should be b2 but was " + m.get("b"));
        check(m.size() == 2, "size must remain 2");
    }

    static void testNullKeyAndNullValue() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        m.put(null, "x");
        m.put("a", null);
        m.replaceAll((k, v) -> k == null ? "nullkey" : (v == null ? "nullval" : v));
        check("nullkey".equals(m.get(null)), "null key value should be nullkey but was " + m.get(null));
        check("nullval".equals(m.get("a")), "null value should be replaced with nullval but was " + m.get("a"));
    }

    static void testConcurrentModificationDetected() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        m.put("a", "1");
        boolean thrown = false;
        try {
            m.replaceAll((k, v) -> {
                m.put("newKey", "2");
                return v;
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during replaceAll must throw ConcurrentModificationException");
    }

    static void testEmptyMapDoesNotInvokeFunction() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        int[] calls = {0};
        m.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function must not be invoked on empty map");
        check(m.isEmpty(), "empty map must remain empty");
    }
}
