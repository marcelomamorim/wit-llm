/*
 * @test
 * @summary Verify ConcurrentHashMap.contains(Object) checks values, not keys, and rejects null
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        check(!m.contains(1), "empty map contains no value");

        m.put("a", 1);
        check(m.contains(1), "present value is found");
        check(!m.contains("a"), "key is not treated as a value");
        check(!m.contains(2), "absent value is not found");
        check(m.contains(Integer.valueOf(1)), "equal boxed value is found");

        m.put("b", 1);
        m.remove("a");
        check(m.contains(1), "value still held by another key is found");

        m.remove("b");
        check(!m.contains(1), "value is not found after removal");

        m.put("c", 3);
        m.clear();
        check(!m.contains(3), "value is not found after clear");

        ConcurrentHashMap<String, String> s = new ConcurrentHashMap<>();
        s.put("k", new String("x"));
        check(s.contains(new String("x")), "equal but non-identical value is found");

        boolean npe = false;
        try {
            m.contains(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null value is rejected with NullPointerException");
    }
}
