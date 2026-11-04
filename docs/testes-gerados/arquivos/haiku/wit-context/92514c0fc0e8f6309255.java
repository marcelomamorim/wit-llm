/*
 * @test
 * @summary WITUP-derived checks for ConcurrentSkipListMap.getOrDefault (present key, absent key, null key)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();

        check(map.getOrDefault("a", 7) == 7, "absent key must return default");

        map.put("a", 1);
        check(map.getOrDefault("a", 7) == 1, "present key must return mapped value");
        check(map.getOrDefault("b", 9) == 9, "other absent key must return default");

        check(map.getOrDefault("x", null) == null, "absent key with null default must return null");

        boolean npeThrown = false;
        try {
            map.getOrDefault(null, 3);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null key must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
