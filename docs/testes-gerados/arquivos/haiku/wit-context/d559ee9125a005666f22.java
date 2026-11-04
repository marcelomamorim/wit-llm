/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.remove(Object) null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);

        boolean npe = false;
        try {
            map.remove(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "remove(null) must throw NullPointerException");
        check(map.size() == 1, "map size must be unchanged after failed remove(null)");
        check(map.containsKey("a"), "existing mapping must survive failed remove(null)");

        check(map.remove("a") == Integer.valueOf(1), "remove of existing key must return its value");
        check(map.remove("a") == null, "remove of absent key must return null");
        check(map.isEmpty(), "map must be empty after removing its only key");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
