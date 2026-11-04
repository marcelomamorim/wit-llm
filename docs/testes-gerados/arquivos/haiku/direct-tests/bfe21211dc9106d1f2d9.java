/*
 * @test
 * @summary Checks containsValue behavior on ConcurrentHashMap, including null rejection
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testPresentAndAbsentValues();
        testEqualsBasedMatch();
        testKeyIsNotValue();
        testAfterRemove();
        testConstructorFromMap();
        testNullValueThrows();
    }

    static void testEmptyMap() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        check(!m.containsValue(Integer.valueOf(1)), "empty map must not contain 1");
    }

    static void testPresentAndAbsentValues() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        check(m.containsValue(Integer.valueOf(1)), "value 1 should be present");
        check(m.containsValue(Integer.valueOf(2)), "value 2 should be present");
        check(!m.containsValue(Integer.valueOf(3)), "value 3 should be absent");
    }

    static void testEqualsBasedMatch() {
        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
        m.put("k", "xy");
        String other = new StringBuilder("x").append("y").toString();
        check(m.containsValue(other), "equal but distinct value should match");
    }

    static void testKeyIsNotValue() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        check(!m.containsValue("a"), "a key must not be matched as a value");
    }

    static void testAfterRemove() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.remove("a");
        check(!m.containsValue(Integer.valueOf(1)), "removed value must not be found");
        check(m.containsValue(Integer.valueOf(2)), "remaining value must be found");
    }

    static void testConstructorFromMap() {
        Map<String, Integer> src = new HashMap<>();
        src.put("k", 7);
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>(src);
        check(m.containsValue(Integer.valueOf(7)), "value copied from source map should be found");
        check(!m.containsValue(Integer.valueOf(8)), "absent value should not be found");
    }

    static void testNullValueThrows() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        boolean thrown = false;
        try {
            m.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsValue(null) must throw NullPointerException");
    }

    static void check(boolean condition) {
        check(condition, "assertion failed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
