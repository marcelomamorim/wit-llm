/*
 * @test
 * @summary Witup-derived checks for ConcurrentHashMap.containsValue null handling and value matching
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        check(!m.containsValue(1));
        m.put("a", 1);
        m.put("b", 2);
        check(m.containsValue(1));
        check(m.containsValue(2));
        check(!m.containsValue(3));
        check(!m.containsValue("1"));
        m.remove("a");
        check(!m.containsValue(1));
        check(m.containsValue(2));
        try {
            m.containsValue(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new AssertionError();
        }
    }
}
