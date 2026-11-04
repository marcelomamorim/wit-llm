/*
 * @test
 * @summary Tests LinkedHashMap.replaceAll null check, value replacement, access order preservation and ConcurrentModificationException detection
 * @run main LinkedHashMapWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testNullFunction();
        testReplacesValuesInInsertionOrder();
        testAccessOrderNotChanged();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunction() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("a", 1);
        try {
            m.replaceAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
        }
        check(m.get("a") == 1, "value changed after NullPointerException");
    }

    static void testReplacesValuesInInsertionOrder() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("a", "1");
        m.put("b", "2");
        m.put("c", "3");
        m.replaceAll((k, v) -> k + v);
        check(m.toString().equals("{a=a1, b=b2, c=c3}"), "unexpected map: " + m);
    }

    static void testAccessOrderNotChanged() {
        Map<String, Integer> m = new LinkedHashMap<>(16, 0.75f, true);
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        m.replaceAll((k, v) -> v + 10);
        check(m.keySet().toString().equals("[a, b, c]"), "order changed: " + m.keySet());
        check(m.toString().equals("{a=11, b=12, c=13}"), "unexpected map: " + m);
    }

    static void testConcurrentModificationDetected() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("a", 1);
        boolean[] done = {false};
        try {
            m.replaceAll((k, v) -> {
                if (!done[0]) {
                    done[0] = true;
                    m.put("z", 0);
                }
                return v + 1;
            });
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
        }
    }
}
