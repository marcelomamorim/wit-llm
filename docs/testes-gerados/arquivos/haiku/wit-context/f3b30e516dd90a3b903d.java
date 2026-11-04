/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.get: public lookup behavior and NullPointerException for null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);

        if (!Integer.valueOf(1).equals(map.get("a"))) {
            throw new RuntimeException("get(existing key) returned unexpected value");
        }
        if (map.get("missing") != null) {
            throw new RuntimeException("get(missing key) should return null");
        }

        boolean npeThrown = false;
        try {
            map.get(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("get(null) on non-empty map should throw NullPointerException");
        }

        ConcurrentSkipListMap<String, Integer> empty = new ConcurrentSkipListMap<>();
        npeThrown = false;
        try {
            empty.get(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("get(null) on empty map should throw NullPointerException");
        }
    }
}
