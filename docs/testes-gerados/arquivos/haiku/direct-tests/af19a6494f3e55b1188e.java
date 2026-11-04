/*
 * @test
 * @summary ConcurrentHashMap values view contains delegates to containsValue
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        check(!map.values().contains(1), "empty map must not contain 1");

        map.put("a", 1);
        map.put("b", 2);
        check(map.values().contains(1), "value 1 must be found");
        check(map.values().contains(2), "value 2 must be found");
        check(!map.values().contains(3), "absent value 3 must not be found");
        check(!map.values().contains("1"), "value of different type must not be found");

        map.remove("a");
        check(!map.values().contains(1), "removed value must not be found");
        check(map.values().contains(2), "remaining value must be found");

        map.put("c", 2);
        map.remove("b");
        check(map.values().contains(2), "value still present under another key must be found");

        map.put("d", 42);
        check(map.values().contains(42), "view must reflect new put");

        Map<String, Integer> source = new HashMap<>();
        source.put("x", 7);
        ConcurrentHashMap<String, Integer> copy = new ConcurrentHashMap<>(source);
        check(copy.values().contains(7), "copy constructor values must be visible");
        check(!copy.values().contains(8), "absent value in copy must not be found");

        ConcurrentHashMap<String, Integer> nullQuery = new ConcurrentHashMap<>();
        nullQuery.put("k", 5);
        boolean npe = false;
        try {
            nullQuery.values().contains(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "contains(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
